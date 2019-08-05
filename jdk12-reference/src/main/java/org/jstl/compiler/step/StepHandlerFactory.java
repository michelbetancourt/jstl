package org.jstl.compiler.step;

import java.util.List;
import java.util.function.Function;

import javax.annotation.Nonnull;

import org.jstl.domain.config.JSTLStep;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "defaultHandler")
public class StepHandlerFactory implements Function<List<JSTLStep>, StepHandler> {

	@Override
	public StepHandler apply(@Nonnull List<JSTLStep> definition) {
		
		return PassThroughStepHandler.passThrough();
	}

}
