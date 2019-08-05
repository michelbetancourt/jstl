package org.jstl.domain.config;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JSTLSubString {

	private Integer startIndex;
	private Integer endIndex;
}
