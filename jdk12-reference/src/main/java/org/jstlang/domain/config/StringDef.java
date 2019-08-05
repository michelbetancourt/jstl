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
public class StringDef {

	private StringCaseDef applyCase;
	private SubStringDef subString;
	private CharPaddingDef charPadding;
}
