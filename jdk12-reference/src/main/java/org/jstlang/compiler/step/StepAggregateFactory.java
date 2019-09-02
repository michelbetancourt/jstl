package org.jstlang.compiler.step;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Function;

import javax.annotation.Nonnull;

import org.jstlang.compiler.step.handler.StepHandler;
import org.jstlang.converters.fasterjackson.FasterJacksonExtObjectConverter;
import org.jstlang.domain.definition.step.StepDef;
import org.jstlang.util.ExtObject;

import com.google.common.collect.Maps;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jstlang.util.SkipSupport;

@RequiredArgsConstructor(staticName = "defaultHandler")
@Slf4j
public class StepAggregateFactory implements Function<List<StepDef>,  Function<Object, Object>> {
    
    static final Map<String, Function<StepDef, StepHandler>> defaultSteps = Maps.newLinkedHashMap();
    
    static {
        defaultSteps.put("stringCase", StringCaseHandlerFactory.defaultHandler());
        defaultSteps.put("subString", StringCaseHandlerFactory.defaultHandler());
        defaultSteps.put("charPadding", StringCaseHandlerFactory.defaultHandler());
        defaultSteps.put("number", NumberDefStepFactory.defaultHandler());
    }

    private Function<Object, ExtObject> extObjectConverter = FasterJacksonExtObjectConverter.typeConverter();
    private Map<String, Function<StepDef, StepHandler>> stepMappings = defaultSteps;
    
    @Override
    public Function<Object, Object> apply(final @Nonnull List<StepDef> definitions) {
        
        Function<Object, Object> handler = val -> {
            log.debug("Starting step transformation for,definitions={}", definitions.size());
            return val;
        };
        Iterator<StepDef> it = definitions.stream().filter(Objects::nonNull).iterator();
        int totalSteps = 0;
        while (it.hasNext()) {
            
            StepDef pathDef = it.next();
            ExtObject properties = extObjectConverter.apply(pathDef);
            // remove skip element to avoid counting it towards the limit 
            properties.removeData("skip");
            if(properties.many()) {
                throw new IllegalStateException(
                        "Steps can only be set singularly to allow proper step ordering,totalProperties="
                                + properties.size() + ",properties=" + properties);
            }
            
            if(!properties.any()) {
                log.debug("Skipping empty step definition");
                continue;
            }
            
            Entry<String, Object> entry = properties.getData()
                .entrySet()
                .stream()
                .findFirst()
                .get();
            String property = entry.getKey();
            log.debug("Found step for,property={}", property);
            log.trace("Found step for,property={},value={}", property, entry.getValue());
            Function<StepDef, StepHandler> stepFactory = stepMappings.get(property);
            if(null == stepFactory) {
                log.debug("No plugin found for step,property={}", property);
                continue;
            }

            Function<Object,Object> skipLogic = val -> {
                // If the step should be skipped, return the input value
                if(SkipSupport.shouldSkip(pathDef.getSkip(), val)) {
                    return val;
                }
                return stepFactory.apply(pathDef) // get the step to execute
                        .apply(val);              // execute step
            };

            handler = handler.andThen(skipLogic);
        }
        
        final int theTotalSteps = totalSteps;
        handler = handler.andThen(val -> {
            log.debug("Steps finished,totalSteps={}", theTotalSteps);
            return val;
        });
        
        return handler;
    }

}
