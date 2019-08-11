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

    private StringCaseHandlerFactory stringDefHandlerFactory = StringCaseHandlerFactory.defaultHandler();
    private NumberDefStepFactory numberDefHandlerFactory = NumberDefStepFactory.defaultHandler();
    private Function<Object, ExtObject> extObjectConverter = FasterJacksonExtObjectConverter.typeConverter();
    private Map<String, Function<StepDef, StepHandler>> stepMappings = Maps.newLinkedHashMap();
    
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
            if(properties.many()) {
                throw new IllegalStateException("Steps can only be set singularly to allow proper step ordering,totalProperties=" + properties.size());
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
            
            handler = handler.andThen(stepFactory.apply(pathDef));
            
        }
        
        final int theTotalSteps = totalSteps;
        handler = handler.andThen(val -> {
            log.debug("Steps finished,totalSteps={}", theTotalSteps);
            return val;
        });
        
        return handler;
    }

}
