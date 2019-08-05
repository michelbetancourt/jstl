package org.jstl.domain.config;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JSTLCharPadding {

	private Character left;
	private Character right;
	private Integer limit;

}
