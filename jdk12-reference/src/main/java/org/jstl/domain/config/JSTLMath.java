package org.jstl.domain.config;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JSTLMath {

	@Builder.Default
	private boolean abs = false;
	@Builder.Default
	private boolean toggleSign = false;

}
