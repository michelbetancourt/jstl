package org.jstlang.domain.definition;

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

    private StringCaseDef casing;
    private SubStringDef subString;
    private CharPaddingDef charPadding;
}
