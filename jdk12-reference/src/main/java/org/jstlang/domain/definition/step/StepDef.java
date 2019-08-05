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

    private IterableDef iterable;
    private NumberDef number;
    private StringDef string;

    @Builder.Default
    private SkipDef skip = SkipDef.builder().build();

}
