package org.jstlang.compiler.step;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import javax.annotation.Nonnull;

import org.jstlang.domain.definition.step.StepDef;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor(staticName = "defaultHandler")
@Slf4j
public class StepAggregateFactory implements Function<List<StepDef>,  Function<Object, Object>> {

    private StringDefStepHandlerFactory stringDefHandlerFactory = StringDefStepHandlerFactory.defaultHandler();
    private NumberDefStepFactory numberDefHandlerFactory = NumberDefStepFactory.defaultHandler();
    
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
            if(null != pathDef.getString()) {
                totalSteps++;
                handler = handler.andThen(stringDefHandlerFactory.apply(pathDef));
            }
            
            if(null != pathDef.getNumber()) {
                totalSteps++;
                handler = handler.andThen(numberDefHandlerFactory.apply(pathDef));
            } 
            
            
        }
        
        final int theTotalSteps = totalSteps;
        handler = handler.andThen(val -> {
            log.debug("Steps finished,totalSteps={}", theTotalSteps);
            return val;
        });
        
        return handler;
    }

}
