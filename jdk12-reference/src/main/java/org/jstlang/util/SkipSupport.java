package org.jstlang.util;

import lombok.extern.slf4j.Slf4j;
import org.jstlang.domain.definition.SkipDef;

import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;

@Slf4j
public class SkipSupport {
    public static boolean shouldSkip(SkipDef skipDef, Object o) {
        // Should skip if at least one of the criteria for skipping is met
        Predicate<Object> nullCheck = val -> null == val  && skipDef.isIfNull();
        Predicate<Object> emptyCheck = val -> isEmpty(val) && skipDef.isIfEmpty();
        return nullCheck.test(o) || emptyCheck.test(o);
    }

    public static boolean isEmpty(Object o) {
        if(o instanceof String) return ((String) o).isEmpty();
        if(o instanceof Map) return ((Map) o).isEmpty();
        if(o instanceof Collection) return ((Collection) o).isEmpty();
        // if you cant determine that the value is empty, then it should not be skipped
        return false;
    }
}
