package org.jstlang.converters.fasterjackson;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Maps;

import lombok.Data;

class FasterJacksonMapConverterTest {

    private MyObject myObject;
    private Map<String, Object> map;
    private FasterJacksonMapConverter converter;
    
    @BeforeEach
    public void before() {

        myObject = new MyObject();
        myObject.number = 123.4D;
        myObject.string = "123.4D";
        map = Maps.newLinkedHashMap();
        map.put("number", 432.1D);
        map.put("string", "432.1D");
        converter = FasterJacksonMapConverter.typeConverter();

    }
    
    @Test
    void testConvert_CustomObject() {
        Map<String, Object> actual = converter.apply(myObject);
        assertThat(actual, is(notNullValue()));
        assertThat(actual.size(), is(2));
        assertThat(actual.get("number"), is(123.4D));
        assertThat(actual.get("string"), is("123.4D"));
    }
    
    @Test
    void testConvert_Map() {
        Map<String, Object> actual = converter.apply(map);
        assertThat(actual, is(notNullValue()));
        assertThat(actual.size(), is(2));
        assertThat(actual.get("number"), is(432.1D));
        assertThat(actual.get("string"), is("432.1D"));
        assertThat(actual, is(map));
    }
    
    @Data
    private static class MyObject {

        private Number number;
        private String string;

    }

}
