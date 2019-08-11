package org.jstlang.compiler.step;

import java.util.Map;
import java.util.function.Function;

import javax.annotation.Nonnull;

import org.jstlang.compiler.step.handler.StepHandler;
import org.jstlang.domain.definition.step.StepDef;
import org.jstlang.domain.definition.step.StringCaseDef;

import com.google.common.collect.Maps;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor(staticName = "defaultHandler")
@Slf4j
public class StringCaseHandlerFactory implements Function<StepDef, StepHandler> {
    
    static final Map<StringCaseDef, StepHandler> defaultSteps = Maps.newLinkedHashMap();
    
    static {
//        defaultSteps.put(StringCaseDef.lower, value)
    }
    
    private Map<StringCaseDef, StepHandler> steps = defaultSteps;

    @Override
    public StepHandler apply(final @Nonnull StepDef stepDef) {
        StringCaseDef stringCase = stepDef.getStringCase();
        
//        switch(stringCase) {
//        case StringCaseDef.lower:   
//        }
        
        return null;
    }

}
