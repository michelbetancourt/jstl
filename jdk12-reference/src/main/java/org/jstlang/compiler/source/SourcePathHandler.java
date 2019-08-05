package org.jstlang.compiler.source;

import javax.annotation.Nonnull;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "sourcePath")
public class SourcePathHandler implements SourceHandler {

	private final @Nonnull JsonPath path;
	
	@Override
	public Object apply(DocumentContext source) {
		return source.read(path);
	}

}
