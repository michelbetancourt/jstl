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
public class NumberDef {

    @Builder.Default
    private boolean abs = false;
    @Builder.Default
    private boolean toggleSign = false;

}
