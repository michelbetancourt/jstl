package org.jstlang.util;

import com.google.common.collect.Maps;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import lombok.experimental.UtilityClass;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.jayway.jsonpath.Option.DEFAULT_PATH_LEAF_TO_NULL;
import static com.jayway.jsonpath.Option.SUPPRESS_EXCEPTIONS;

@UtilityClass
public class AggregateOps {

    private @Nonnull
    Configuration jsonConfig = Configuration.builder()
            .options(DEFAULT_PATH_LEAF_TO_NULL, SUPPRESS_EXCEPTIONS).build();

    public static Object sum(Object obj, List<String> paths) {

        if(!(obj instanceof Collection)) {
            return obj;
        }
        Collection collection = (Collection) obj;


        // TODO: iterate over collection and aggregate based on fields provided
        collection.stream()
                .map(item -> {
                    DocumentContext read = JsonPath.parse(item, jsonConfig);

                    Map<String,Object> results = Maps.newLinkedHashMap();
                    paths.stream()
                        .forEach(path -> {
                            Object value = read.read(path);
                            Optional.ofNullable(value)
                                    .ifPresent(val -> results.put(path, value));
                        });

                    return results;
                });

        //TODO: look into collecting all maps and adding the individual keys together
//                .collect();
        return null;
    }
}
