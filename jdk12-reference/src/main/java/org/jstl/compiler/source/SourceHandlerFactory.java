package org.jstl.compiler.source;

import java.util.function.Function;

import org.jstl.domain.config.JSTLSource;

import com.jayway.jsonpath.JsonPath;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "defaultHandler")
public class SourceHandlerFactory implements Function<JSTLSource, SourceHandler> {

	@Override
	public SourceHandler apply(JSTLSource definition) {
		
		return SourcePathHandler.sourcePath(JsonPath.compile(definition.getPath()));
	}

}
