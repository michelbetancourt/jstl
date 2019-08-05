package org.jstl.domain.config;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JSTLTarget {

	private String path;
	private Class<?> type;
	
	@Builder.Default
	private JSTLSkip skip = JSTLSkip.builder().build();

}
