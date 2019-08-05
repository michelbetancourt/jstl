package org.jstl.compiler.step;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "passThrough")
public class PassThroughStepHandler implements StepHandler {

	@Override
	public Object apply(Object value) {
		return value;
	}

}
