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

    public static Object invertCase(Object val) {
        
        if (!(val instanceof String)) {
            return val;
        }
        
        return ((String)val).chars()
           .map(c -> Character.isUpperCase(c) ? Character.toLowerCase(c) : Character.toUpperCase(c))
           .collect(StringBuilder::new, (builder, intVal) -> builder.append((char) intVal), (b1, b2) -> b1.append(b2))
           .toString();
        
    }
    
}
