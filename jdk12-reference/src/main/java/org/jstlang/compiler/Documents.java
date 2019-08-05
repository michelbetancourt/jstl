package org.jstlang.compiler;

import com.jayway.jsonpath.DocumentContext;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Documents {

    private final DocumentContext sourceObject;
    private final DocumentContext targetObject;

}
