package org.jstl.domain.config;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JSTLSkip {

	@Builder.Default
	private boolean ifEmpty = true;
	@Builder.Default
	private boolean ifNull = true;

}
