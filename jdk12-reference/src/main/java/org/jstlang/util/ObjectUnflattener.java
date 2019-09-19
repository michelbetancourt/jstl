package org.jstlang.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jayway.jsonpath.Configuration;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.jayway.jsonpath.Option.DEFAULT_PATH_LEAF_TO_NULL;
import static com.jayway.jsonpath.Option.SUPPRESS_EXCEPTIONS;

public class ObjectUnflattener {
    private static Configuration jsonConfig = Configuration.builder()
            .options(DEFAULT_PATH_LEAF_TO_NULL, SUPPRESS_EXCEPTIONS).build();
    public static Map<String,Object> unflatten(Map<String,Object> input) {

        Map<String,Object> unflattenedMap = Maps.newHashMap();

        input.forEach( (key,value) -> {
                    Map<String,Object> root = unflattenedMap;
                    // Go through every node in the path and create it if does not exist
                    Object nextValue = null;
                    List<String> splitKeys = Lists.newArrayList(key.split("\\."));
                    String lastKey = splitKeys.get(splitKeys.size()-1);
                    if(splitKeys.size()>1){
                        splitKeys = splitKeys.subList(0,splitKeys.size()-1);
                        for (String path : splitKeys) {
                            nextValue = root.get(path);
                            if (null == nextValue) {
                                Map<String, Object> newRoot = Maps.newLinkedHashMap();
                                root.put(path, newRoot);
                                root = newRoot;
                            } else if (nextValue instanceof Map) {
                                root = (Map<String, Object>) nextValue;
                            }
                        }
                    }
                    Object unflattenedValue = ObjectUnflattener.typeChecker(value);
                    root.put(lastKey, unflattenedValue);
                });

        return unflattenedMap;
    }

    public static Object typeChecker(Object element) {
        if(element instanceof Map) {
            return ObjectUnflattener.unflatten((Map<String,Object>) element);
        }else if (element instanceof Collection) {
            return ObjectUnflattener.flattenList((Collection<Object>) element);
        }

        return element;
    }

    public static Object flattenList(Collection<Object> collection) {
        return collection.stream().map( element ->
            ObjectUnflattener.typeChecker(element)
        ).collect(Collectors.toList());
    }
}
