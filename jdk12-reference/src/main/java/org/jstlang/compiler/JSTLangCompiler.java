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
import org.jstlang.domain.definition.FieldPathDef;
import org.jstlang.domain.definition.ObjectDef;
import org.jstlang.domain.definition.SourceDef;
import org.jstlang.domain.definition.TargetDef;
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
    private @Nonnull Function<SourceDef, TargetDef> sourceToTargetProvider = source -> TargetDef.builder()
            // TODO determine other options
            .path(source.getPath())
            .build();

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

        List<FieldPathDef> pathDefs = Optional.ofNullable(objectDef.getFields()).orElse(Collections.emptyList());

        if (pathDefs.isEmpty()) {
            throw new IllegalArgumentException("No mappings to compile!");
        }

        Consumer<Documents> func = doc -> {
            log.info("Starting conversion");
            log.debug("Starting conversion,sourceDocument={},targetDocument={}", doc.getSourceObject(),
                    doc.getTargetObject());
        };

        Iterator<FieldPathDef> it = pathDefs.stream().filter(Objects::nonNull).iterator();
        int totalBindings = 0;
        while (it.hasNext()) {
            // count the total bindings
            totalBindings++;
            
            // obtain the next definition
            FieldPathDef pathDef = it.next();
            
            // determine the source, which is required
            SourceDef source = pathDef.getGet();
            if(null == source) {
                if(!objectDef.isSkipMissingSource()) {
                    throw new IllegalStateException("Source is missing on fieldDefinition=" + pathDef);
                }
                log.warn("Missing source for fieldDefinition=" + pathDef);
                continue;
            }
            SourceHandler sourceHandler = sourceHandlerFactory.apply(source);
            List<StepDef> steps = Optional.ofNullable(pathDef)
                    .map(FieldPathDef::getSteps)
                    .orElse(Collections.emptyList());
            totalBindings += steps.size();
            Function<Object, Object> stepHandler = stepHandlerFactory.apply(steps);
            
            TargetDef target = Optional.ofNullable(pathDef.getPut())
                    .orElseGet(() -> sourceToTargetProvider.apply(source));
            
            TargetHandler targetHandler = targetHandlerFactory.apply(target);
            func = func.andThen(SourceToTargetBinder.binder(sourceHandler, stepHandler, targetHandler, source.getSourceIs()));
        }

        return TargetObjectBinder.defaultBinder(func)
                .totalBindings(totalBindings)
                .targetConverter(FasterJacksonObjectConverter.typeConverter(objectDef.getOutputType()));
    }
}
