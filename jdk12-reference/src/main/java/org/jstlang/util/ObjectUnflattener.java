package org.jstlang.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor(staticName = "getDefault")
public class ObjectUnflattener implements Function<Object, Object> {

    private final @NonNull String splitEscape = "\\";
    private final @NonNull String splitterExpression = "(?<!\\\\)\\.";

    /**
     * This is the entry point to the Object Unflattener logic.
     * @param input - Any Object
     * @return - An unflattened version of the Object
     */
    public Object apply(Object input) {
        return this.typeChecker(input);
    }

    /**
     * This is the entry point to the Object Unflattener logic.
     * This method takes a Map and will check if every key in the map can be unflattened based on the splitting criteria.
     * This action is then performed recursively over every value in the Map, leading to a Depth First traversal and
     * transformation of the initial input structure
     * @param input
     * @return
     */
    public Map<String,Object> unflattenMap(Map<String,Object> input) {
        Map<String,Object> unflattenedMap = Maps.newHashMap();

        input.forEach( (key,value) -> {
                    Map<String,Object> root = unflattenedMap;
                    // Go through every node in the path and create it if does not exist
                    Object nextValue = null;
                    // This is the regex expression "(?<!\\)\." which will match every "." that does not have a \ in front of it
                    // this provides a way for the user to be able to use the "." in a key without it being unflattened
                    List<String> splitKeys = Lists.newArrayList(key.split(splitterExpression));
                    String lastKey = splitKeys.get(splitKeys.size()-1);
                    if(splitKeys.size()>1){
                        splitKeys = splitKeys.subList(0,splitKeys.size()-1);
                        for (String path : splitKeys) {
                            // recursively iterate over the values
                            path = path.replace(splitEscape, "");
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
                    // recursively iterate over the values
                    Object unflattenedValue = this.typeChecker(value);
                    // remove escape character from the key
                    lastKey = lastKey.replace(splitEscape, "");
                    root.put(lastKey, unflattenedValue);
                });

        return unflattenedMap;
    }

    /**
     * This is a helper method that will check the type of the input and executes
     * the appropriate logic based on the type
     *
     * @param element - An object of any type
     * @return - Unflattened version of the object
     */
    private Object typeChecker(Object element) {
        if(element instanceof Map) {
            return this.unflattenMap((Map<String,Object>) element);
        }else if (element instanceof Collection) {
            return this.unflattenList((Collection<Object>) element);
        }

        return element;
    }

    /**
     * This method iterates over a collection and calls the type checker helper method for every element
     * in the collection.
     *
     * It allows for every element in the collection to be properly unflattened
     *
     * @param collection - A collection of objects
     * @return - A list of unflattened objects
     */
    public Object unflattenList(Collection<Object> collection) {
        return collection.stream().map( element ->
            this.typeChecker(element)
        ).collect(Collectors.toList());
    }
}
