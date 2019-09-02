package org.jstlang.domain.definition;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SourceDef {

    private String value;
    private String path;

    private List<String> values;
    private String joiner;

    private List<String> findFirst;
    private List<String> findLast;

    private String defaultValue;
    @Builder.Default
    private ValueSourceDef sourceIs = ValueSourceDef.source;
}
