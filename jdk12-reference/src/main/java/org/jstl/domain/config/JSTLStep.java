package org.jstl.domain.config;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class JSTLStep {

	private JSTLIteratable iterable;
	private JSTLMath math;
	
	@Builder.Default
	private JSTLSkip skip = JSTLSkip.builder().build();

}
