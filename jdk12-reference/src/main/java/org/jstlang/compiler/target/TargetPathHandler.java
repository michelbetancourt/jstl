package org.jstlang.compiler.target;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jstlang.domain.definition.SkipDef;
import org.jstlang.util.SkipSupport;

@RequiredArgsConstructor
@Setter
@Accessors(fluent = true)
public class TargetPathHandler implements TargetHandler {

    private final @Nonnull JsonPath targetPath;
    private final @Nonnull List<String> splitPaths;

    private @Nonnull Function<Object, Object> typeConverter = Function.identity();

    private JsonPath splitPath = null;
    private String lastKey = null;
    private SkipDef skipDef = SkipDef.builder().build();
    private @Nonnull JsonPath rootPath = JsonPath.compile("$");

    public static TargetPathHandler targetPath(@Nonnull String targetPathString) {
        List<String> splitPaths = splitPaths(targetPathString);
        JsonPath targetPath = JsonPath.compile(targetPathString);

        JsonPath splitPath = null;
        String last = null;
        if (splitPaths.size() > 1) {

            last = Iterables.getLast(splitPaths);
            splitPath = JsonPath.compile("$." + last);
            splitPaths.remove(splitPaths.size() - 1);
        }

        return new TargetPathHandler(targetPath, splitPaths).splitPath(splitPath).lastKey(last);
    }

    static List<String> splitPaths(String path) {
        return Arrays.stream(path.split("[\\.\\$]")).filter(Objects::nonNull).filter(val -> !val.trim().isEmpty())
                .collect(Collectors.toList());
    }

    @Override
    public void accept(final Object value, final DocumentContext targetObject) {

        // check if the value should be skipped
        if(SkipSupport.shouldSkip(skipDef, value)){
            return;
        }

        Object theValue = typeConverter.apply(value);

        // for standard paths just apply directly
        if (null == splitPath) {
            targetObject.set(targetPath, theValue);
            return;
        }

        Map<String, Object> root = targetObject.read(rootPath);

        // Go through every node in the path and create it if does not exist
        Object nextValue = null;
        for (String path : splitPaths) {
            nextValue = root.get(path);
            if (null == nextValue) {
                Map<String, Object> newRoot = Maps.newLinkedHashMap();
                root.put(path, newRoot);
                root = newRoot;
            } else if (nextValue instanceof Map) {
                root = (Map<String, Object>) nextValue;
            }
        }

        // add the value to the last node in the path
        root.put(lastKey, theValue);
    }

}
