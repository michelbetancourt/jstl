package org.jstlang.compiler.step;

import java.util.function.Function;

import javax.annotation.Nonnull;

import org.jstlang.compiler.step.handler.StepHandler;
import org.jstlang.domain.definition.step.StepDef;
import org.jstlang.domain.definition.step.StringDef;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor(staticName = "defaultHandler")
@Slf4j
public class StringDefStepHandlerFactory implements Function<StepDef, StepHandler> {

    @Override
    public StepHandler apply(final @Nonnull StepDef stepDef) {
        StringDef stringDef = stepDef.getString();
        if(null != stringDef.getCasing()) {
            
        }
        
        if(null != stringDef.getCharPadding()) {
            
        }
        
        if(null != stringDef.getSubString()) {
            
        }
        
        return null;
    }

}
