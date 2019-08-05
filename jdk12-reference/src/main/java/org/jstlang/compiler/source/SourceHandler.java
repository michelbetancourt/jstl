package org.jstlang.compiler.source;

import java.util.function.Function;

import com.jayway.jsonpath.DocumentContext;

public interface SourceHandler extends Function<DocumentContext, Object> {

}
