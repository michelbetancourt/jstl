package org.jstlang.util;

import java.util.Optional;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SubStrings {

    public Object subString(final Object val, final Integer start, final Integer end) {
        
        if (!(val instanceof String)) {
            return val;
        }
        
        String theString = (String)val;
        int startIndex = Optional.ofNullable(start)
                .orElse(0);
        int endIndex = Optional.ofNullable(end)
                .orElseGet(() -> theString.length() - 1);
        
        return theString.substring(startIndex, endIndex);
    }
}
