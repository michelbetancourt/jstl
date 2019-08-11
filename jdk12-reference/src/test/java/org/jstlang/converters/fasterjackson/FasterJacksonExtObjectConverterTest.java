package org.jstlang.converters.fasterjackson;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Map;

import org.jstlang.util.ExtObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Maps;

import lombok.Data;

class FasterJacksonExtObjectConverterTest {

    private MyObject myObject;
    private Map<String, Object> map;
    private FasterJacksonExtObjectConverter converter;
    
    @BeforeEach
    public void before() {

        myObject = new MyObject();
        myObject.number = 123.4D;
        myObject.string = "123.4D";
        map = Maps.newLinkedHashMap();
        map.put("number", 432.1D);
        map.put("string", "432.1D");
        converter = FasterJacksonExtObjectConverter.typeConverter();

    }
    
    @Test
    void testConvert_CustomObject() {
        ExtObject actual = converter.apply(myObject);
        assertThat(actual, is(notNullValue()));
        assertThat(actual.any(), is(true));
        assertThat(actual.many(), is(true));
        assertThat(actual.size(), is(2));
        assertThat(actual.getData().get("number"), is(123.4D));
        assertThat(actual.getData().get("string"), is("123.4D"));
    }
    
    @Test
    void testConvert_Map() {
        ExtObject actual = converter.apply(map);
        assertThat(actual, is(notNullValue()));
        assertThat(actual.any(), is(true));
        assertThat(actual.many(), is(true));
        assertThat(actual.size(), is(2));
        assertThat(actual.getData().get("number"), is(432.1D));
        assertThat(actual.getData().get("string"), is("432.1D"));
    }
    
    @Data
    private static class MyObject {

        private Number number;
        private String string;

    }

}
