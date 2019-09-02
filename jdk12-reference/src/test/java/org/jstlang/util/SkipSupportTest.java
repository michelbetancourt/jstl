package org.jstlang.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.jstlang.domain.definition.SkipDef;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class SkipSupportTest {

    private SkipDef skipDef;
    private Object input;

    @BeforeEach
    void setUp() {
        input = "value";
        skipDef = SkipDef.builder().build();
    }

    @Test
    void testShouldSkip() {
        input = "";
        boolean result = SkipSupport.shouldSkip(skipDef, input);
        assertThat(result, is(true));

        input = "";
        result = SkipSupport.shouldSkip(skipDef, null);
        assertThat(result, is(true));
    }

    @Test
    void testShouldSkip_NoSkipping() {
        input = "";
        skipDef.setIfEmpty(false);
        boolean result = SkipSupport.shouldSkip(skipDef, input);
        assertThat(result, is(false));

        skipDef = SkipDef.builder().ifNull(false).build();
        result = SkipSupport.shouldSkip(skipDef, null);
        assertThat(result, is(false));
    }

    @Test
    void testIsEmpty() {
        // check that map is empty
        Object o = Maps.newLinkedHashMap();
        boolean result = SkipSupport.isEmpty(o);
        assertThat(result, is(true));

        // check that array is empty
        o = Lists.newArrayList();
        result = SkipSupport.isEmpty(o);
        assertThat(result, is(true));

        // check that the string is empty
        o = "";
        result = SkipSupport.isEmpty(o);
        assertThat(result, is(true));

        // check that null is not considered as empty
        result = SkipSupport.isEmpty(null);
        assertThat(result, is(false));

        // check that whatever is not defined in the isEmpty checks returns false
        result = SkipSupport.isEmpty(12);
        assertThat(result, is(false));

    }
}