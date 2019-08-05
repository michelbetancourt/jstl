package org.jstl.domain.config;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JSTLStep {

	private JSTLIteratable iterable;
	private JSTLMath math;
	
	@Builder.Default
	private JSTLSkip skip = JSTLSkip.builder().build();

}
