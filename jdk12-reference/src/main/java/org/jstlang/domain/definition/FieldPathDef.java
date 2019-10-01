package org.jstlang.domain.definition;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class FieldPathDef {

    private SourceDef get;
    private TargetDef put;
    @JsonProperty("do")
    private List<StepDef> steps;

}
