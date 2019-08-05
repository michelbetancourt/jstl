package org.jstlang.compiler.step;

import java.util.List;
import java.util.function.Function;

import javax.annotation.Nonnull;

import org.jstlang.domain.definition.StepDef;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "defaultHandler")
public class StepHandlerFactory implements Function<List<StepDef>, StepHandler> {

    @Override
    public StepHandler apply(@Nonnull List<StepDef> definition) {

        return PassThroughStepHandler.passThrough();
    }

}
