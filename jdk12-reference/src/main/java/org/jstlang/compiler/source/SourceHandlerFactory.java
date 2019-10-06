package org.jstlang.compiler.source;

import java.util.function.Function;
import org.jstlang.domain.definition.SourceDef;
import com.jayway.jsonpath.JsonPath;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "defaultHandler")
public class SourceHandlerFactory implements Function<SourceDef, SourceHandler> {

    @Override
    public SourceHandler apply(SourceDef definition) {
        // if values is present then execute the join logic
        if(definition.getValues() != null) {
            return JoinSourceHandler.sourcePath(definition.getValues(), definition.getJoiner());
        }
        return SourcePathHandler.sourcePath(JsonPath.compile(definition.getPath()));
    }

}
