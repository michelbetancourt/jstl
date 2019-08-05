package org.jstlang.compiler;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.annotation.Nonnull;

import org.jstlang.compiler.source.SourceHandler;
import org.jstlang.compiler.source.SourceHandlerFactory;
import org.jstlang.compiler.step.StepHandler;
import org.jstlang.compiler.step.StepHandlerFactory;
import org.jstlang.compiler.target.TargetHandler;
import org.jstlang.compiler.target.TargetHandlerFactory;
import org.jstlang.converters.fasterjackson.FasterJacksonObjectConverter;
import org.jstlang.domain.definition.ObjectDef;
import org.jstlang.domain.definition.PathDef;
import org.jstlang.domain.definition.StepDef;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor(staticName = "newInstance")
@Setter
@Accessors(fluent = true)
@Slf4j
public class JSTLangCompiler {

    private @Nonnull SourceHandlerFactory sourceHandlerFactory = SourceHandlerFactory.defaultHandler();
    private @Nonnull StepHandlerFactory stepHandlerFactory = StepHandlerFactory.defaultHandler();
    private @Nonnull TargetHandlerFactory targetHandlerFactory = TargetHandlerFactory.defaultHandler();

    public Function<Object, Object> compile(@Nonnull ObjectDef objectDef) {

        List<PathDef> pathDefs = Optional.ofNullable(objectDef.getPaths()).orElse(Collections.emptyList());

        if (pathDefs.isEmpty()) {
            throw new IllegalArgumentException("No mappings to compile!");
        }

        Consumer<Documents> func = doc -> {
            log.info("Starting conversion");
            log.debug("Starting conversion,sourceDocument={},targetDocument={}", doc.getSourceObject(),
                    doc.getTargetObject());
        };

        Iterator<PathDef> it = pathDefs.stream().filter(Objects::nonNull).iterator();
        int totalBindings = 0;
        while (it.hasNext()) {
            totalBindings++;
            PathDef pathDef = it.next();
            SourceHandler sourceHandler = sourceHandlerFactory.apply(pathDef.getSource());
            List<StepDef> steps = Optional.ofNullable(pathDef)
                    .map(PathDef::getSteps)
                    .orElse(Collections.emptyList());
            totalBindings += steps.size();
            StepHandler stepHandler = stepHandlerFactory.apply(steps);
            TargetHandler targetHandler = targetHandlerFactory.apply(pathDef.getTarget());
            func = func.andThen(SourceToTargetBinder.binder(sourceHandler, stepHandler, targetHandler));
        }

        return TargetObjectBinder.defaultBinder(func)
                .totalBindings(totalBindings)
                .targetConverter(FasterJacksonObjectConverter.typeConverter(objectDef.getTargetType()));
    }
}
