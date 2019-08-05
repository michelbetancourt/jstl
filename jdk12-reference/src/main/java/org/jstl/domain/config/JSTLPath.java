package org.jstl.domain.config;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class JSTLPath {

	private JSTLSource source;
	private JSTLTarget target;
	
	@Builder.Default
	private JSTLValueSource sourceIs = JSTLValueSource.source;
	
	private List<JSTLStep> steps;
	
}
 