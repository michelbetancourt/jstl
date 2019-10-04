package org.jstlang.domain.definition.step;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jstlang.domain.definition.AggregatesDef;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AggregateStep {
    private AggregatesDef op;
    private List<String> fields;
}
