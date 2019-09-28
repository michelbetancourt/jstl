package org.jstlang.domain.lambda;

import lombok.Data;

import java.util.Map;

@Data
public class JstlLamdaInput {
    private Map<String,Object> sourceObject;
    private Map<String,Object> mappings;
}
