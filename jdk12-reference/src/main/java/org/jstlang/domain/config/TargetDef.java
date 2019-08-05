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
public class TargetDef {

    private String path;
    private Class<?> type;

    @Builder.Default
    private SkipDef skip = SkipDef.builder().build();

}
