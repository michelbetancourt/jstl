package org.jstlang.domain.definition;

import java.util.List;

import org.jstlang.domain.definition.step.StepDef;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PathDef {

    private SourceDef source;
    private TargetDef target;

    @Builder.Default
    private ValueSourceDef sourceIs = ValueSourceDef.source;

    private List<StepDef> steps;

}
