package org.jstlang.compiler.source;

import java.util.function.Function;

import org.jstlang.compiler.target.TargetPathHandler;
import org.jstlang.domain.definition.PathDef;
import org.jstlang.domain.definition.SourceDef;

import com.jayway.jsonpath.JsonPath;

import lombok.RequiredArgsConstructor;
import org.jstlang.domain.definition.ValueSourceDef;

@RequiredArgsConstructor(staticName = "defaultHandler")
public class SourceHandlerFactory implements Function<PathDef, SourceHandler> {

    @Override
    public SourceHandler apply(PathDef pathDef) {
        return SourcePathHandler.sourcePath(JsonPath.compile(pathDef.getSource().getPath()));
    }

}
