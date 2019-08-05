package org.jstlang.domain.config;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SkipDef {

	@Builder.Default
	private boolean ifEmpty = true;
	@Builder.Default
	private boolean ifNull = true;

}
