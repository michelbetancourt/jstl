package org.jstlang.util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExtObjectTest {

    private ExtObject obj;

    @BeforeEach
    public void beforeEach() {

        obj = new ExtObject();

    }

    @Test
    public void test_NoEntry() {

        assertThat(obj, is(notNullValue()));
        assertThat(obj.size(), is(0));
        assertThat(obj.any(), is(false));
        assertThat(obj.many(), is(false));

    }

    @Test
    public void test_OneEntry() {

        assertThat(obj, is(notNullValue()));

        obj.setData("key1", "v1");

        assertThat(obj.size(), is(1));
        assertThat(obj.any(), is(true));
        assertThat(obj.many(), is(false));

    }

    @Test
    void test_TwoEntries() {

        assertThat(obj, is(notNullValue()));

        obj.setData("key1", "v1");
        obj.setData("key2", "v2");

        assertThat(obj.size(), is(2));
        assertThat(obj.any(), is(true));
        assertThat(obj.many(), is(true));

    }

}
