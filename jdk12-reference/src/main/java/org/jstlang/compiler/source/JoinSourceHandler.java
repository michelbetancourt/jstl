package org.jstlang.compiler.source;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor(staticName = "sourcePath")
public class JoinSourceHandler implements SourceHandler{
    private final @Nonnull List<String> pathStrings;
    private final String joiner;

    @Override
    public Object apply(DocumentContext source) {
        return pathStrings.stream()
                .map(path -> SourcePathHandler.sourcePath(JsonPath.compile(path)))
                .map(func -> func.apply(source))
                .map(Objects::toString)
                .collect(Collectors.joining(Optional.ofNullable(joiner).orElse("")));
    }

}
