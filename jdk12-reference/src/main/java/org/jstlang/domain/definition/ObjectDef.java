package org.jstlang.domain.definition;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ObjectDef {

    @Default
    private boolean skipMissingField = true;
    @Default
    private boolean skipMissingSource = true;
    
    
    private List<FieldPathDef> fieldPaths;
    private Class<?> outputType;
}
