package org.jstlang.util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StringCasingTest {

    private Object obj;
    
    @BeforeEach
    public void beforeEach() {
        obj = new Object();
    }
    
    @Test
    void testToLower_PlainObject() {
        Object actual = StringCasing.toLower(obj);
        assertThat(actual, is(obj));
    }
    
    @Test
    void testToLower_Null() {
        Object actual = StringCasing.toLower(null);
        assertThat(actual, is(nullValue()));
    }
    
    @Test
    void testToLower_String() {
        Object actual = StringCasing.toLower(" JUNIT  TEST ");
        assertThat(actual, is(" junit  test "));
    }
    
    @Test
    void testToUpper_PlainObject() {
        Object actual = StringCasing.toUpper(obj);
        assertThat(actual, is(obj));
    }
    
    @Test
    void testToUpper_Null() {
        Object actual = StringCasing.toUpper(null);
        assertThat(actual, is(nullValue()));
    }
    
    @Test
    void testToUpper_String() {
        Object actual = StringCasing.toUpper(" junit  test ");
        assertThat(actual, is(" JUNIT  TEST "));
    }
    
}
