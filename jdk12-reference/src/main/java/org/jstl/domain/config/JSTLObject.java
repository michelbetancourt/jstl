package org.jstl.domain.config;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JSTLObject {

	private JSTLSource source;
	private JSTLTarget target;
	
	@Builder.Default
	private JSTLValueSource sourceIs = JSTLValueSource.source;
	
	private List<JSTLStep> steps;
	
}
 