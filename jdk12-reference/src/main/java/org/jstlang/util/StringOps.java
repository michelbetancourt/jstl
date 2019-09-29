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

    public static Object padding(Object val, String direction, Character character, Integer limit) {
        if (!(val instanceof String)) {
            return val;
        }

        // define builder and set its value to the input
        StringBuilder sb = new StringBuilder();
        sb.append(val.toString());

        // determine how many times the output string should be padded
        int paddingSize = limit - val.toString().length();

        // apply padding
        for(int i=0; i < paddingSize; i++) {
            if(direction.equalsIgnoreCase("left")){
                sb.insert(0, character);
            }else {
                sb.append(character);
            }
        }

        return sb.toString();
    }
    
}
