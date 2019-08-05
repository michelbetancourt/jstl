package org.jstlang.compiler.source;

import java.util.function.Function;

import org.jstlang.domain.config.SourceDef;

import com.jayway.jsonpath.JsonPath;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "defaultHandler")
public class SourceHandlerFactory implements Function<SourceDef, SourceHandler> {

    @Override
    public SourceHandler apply(SourceDef definition) {

        return SourcePathHandler.sourcePath(JsonPath.compile(definition.getPath()));
    }

}
