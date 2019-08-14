package org.jstlang.util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StringOpsTest {

    private Object obj;
    
    @BeforeEach
    public void beforeEach() {
        obj = new Object();
    }
    
    @Test
    void testReverse_PlainObject() {
        Object actual = StringOps.reverse(obj);
        assertThat(actual, is(obj));
    }
    
    @Test
    void testReverse_Null() {
        Object actual = StringOps.reverse(null);
        assertThat(actual, is(nullValue()));
    }
    
    @Test
    public void testReverse_String() {
        Object actual = StringOps.reverse(" junit  test ");
        assertThat(actual, is(" tset  tinuj "));
    }

}
