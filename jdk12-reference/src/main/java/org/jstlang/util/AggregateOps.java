package org.jstlang.util;

import com.google.common.collect.Maps;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.jayway.jsonpath.Option.DEFAULT_PATH_LEAF_TO_NULL;
import static com.jayway.jsonpath.Option.SUPPRESS_EXCEPTIONS;

@UtilityClass
@Slf4j
public class AggregateOps {

    private @Nonnull
    Configuration jsonConfig = Configuration.builder()
            .options(DEFAULT_PATH_LEAF_TO_NULL, SUPPRESS_EXCEPTIONS).build();

    public static Object sum(Object obj, List<String> paths) {

        if(!(obj instanceof Collection)) {
            return obj;
        }
        Collection<Object> collection = (Collection) obj;

        Object result = collection.stream()
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
                })
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(entry -> {
                    String key = entry.getKey();
                    // remove the $. from the key
                    return key.replace("$.", "");
                }, Map.Entry::getValue, (e1, e2) -> {
                    try{
                        BigDecimal num1 = new BigDecimal(e1.toString());
                        BigDecimal num2 = new BigDecimal(e2.toString());
                        return num1.add(num2);
                    }catch (NumberFormatException e) {
                        log.error("One or more of the inputs is not a Number");
                        return BigDecimal.ZERO;
                    }
                }));

        return result;
    }
}
