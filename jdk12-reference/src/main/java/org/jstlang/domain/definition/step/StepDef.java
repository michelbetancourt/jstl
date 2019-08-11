package org.jstlang.domain.definition.step;

import org.jstlang.domain.definition.SkipDef;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StepDef {

    private StringCaseDef stringCase;
    private SubStringDef subString;
    private CharPaddingDef charPadding;
    private NumberDef number;
    private IterableDef iterable;
    
    @Builder.Default
    private SkipDef skip = SkipDef.builder().build();

}
