package org.jstlang.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringOps {

    public static Object reverse(Object val) {
        
        if (!(val instanceof String)) {
            return val;
        }
        
        StringBuilder buffer = new StringBuilder((String)val);
        return buffer.reverse().toString();
        
    }
    
}
