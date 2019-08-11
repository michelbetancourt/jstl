package org.jstlang.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringCasing {

    public static Object toUpper(Object val) {

        if (!(val instanceof String)) {
            return val;
        }

        return ((String) val).toUpperCase();

    }

    public static Object toLower(Object val) {

        if (!(val instanceof String)) {
            return val;
        }

        return ((String) val).toLowerCase();

    }
}
