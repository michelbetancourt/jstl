package org.jstlang.util;

import lombok.experimental.UtilityClass;

import java.util.Collection;

@UtilityClass
public class AggregateOps {

    public static Object sum(Object obj) {

        if(!(obj instanceof Collection)) {
            return obj;
        }
        Collection collection = (Collection) obj;

        // TODO: iterate over collection and aggregate based on fields provided
        return null;
    }
}
