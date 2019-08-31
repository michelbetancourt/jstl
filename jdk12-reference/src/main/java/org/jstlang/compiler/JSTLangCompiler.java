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
import org.jstlang.compiler.step.StepAggregateFactory;
import org.jstlang.compiler.target.TargetHandler;
import org.jstlang.compiler.target.TargetHandlerFactory;
import org.jstlang.converters.fasterjackson.FasterJacksonObjectConverter;
import org.jstlang.domain.definition.ObjectDef;
import org.jstlang.domain.definition.PathDef;
import org.jstlang.domain.definition.step.StepDef;

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
    private @Nonnull StepAggregateFactory stepHandlerFactory = StepAggregateFactory.defaultHandler();
    private @Nonnull TargetHandlerFactory targetHandlerFactory = TargetHandlerFactory.defaultHandler();

    /**
     * This method is responsible for setting up the configurations for all the mappings under a single Object Definition
     *
     * @param objectDef - Contains a set of instructions to be executed in the order that they were created
     *
     * @return - A lambda function that will apply a series of manipulations defined by the compilation/configuration process
     *              to its input object and return an object of the type specified by the targetType.
     *          Target type defaults to a Map<String,Object>
     */
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

            // prepare the source handling step
            SourceHandler sourceHandler = sourceHandlerFactory.apply(pathDef);
            // Extract and prepare all steps to be executed with a single function call
            List<StepDef> steps = Optional.ofNullable(pathDef)
                    .map(PathDef::getSteps)
                    .orElse(Collections.emptyList());
            Function<Object, Object> stepHandler = stepHandlerFactory.apply(steps);
            // prepare target creating step
            TargetHandler targetHandler = targetHandlerFactory.apply(pathDef.getTarget());
            // Gather the configurations into a single method for later execution
            func = func.andThen(SourceToTargetBinder.binder(sourceHandler, stepHandler, targetHandler, pathDef.getSourceIs()));

            totalBindings += steps.size();
        }

        return TargetObjectBinder.defaultBinder(func)
                .totalBindings(totalBindings)
                .targetConverter(FasterJacksonObjectConverter.typeConverter(objectDef.getTargetType()));
    }
}
