package org.jstlang.compiler.step;

import java.util.function.Function;

import javax.annotation.Nonnull;

import org.jstlang.compiler.step.handler.StepHandler;
import org.jstlang.domain.definition.step.StepDef;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor(staticName = "defaultHandler")
@Slf4j
public class NumberDefStepFactory implements Function<StepDef, StepHandler> {

    @Override
    public StepHandler apply(final @Nonnull StepDef stepDef) {
        
        return null;
    }

}
