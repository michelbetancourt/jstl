package org.jstl.compiler.target;

import java.util.function.BiConsumer;

import com.jayway.jsonpath.DocumentContext;

public interface TargetHandler extends BiConsumer<Object, DocumentContext> {

}
