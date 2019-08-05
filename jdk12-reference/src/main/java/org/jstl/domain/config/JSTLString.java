package org.jstl.domain.config;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JSTLString {

	private JSTLStringCase applyCase;
	private JSTLSubString subString;
	private JSTLCharPadding charPadding;
}
