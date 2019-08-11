package org.jstlang.converters.fasterjackson;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Maps;

import lombok.Data;

class FasterJacksonObjectConverterTest {

    private MyObject myObject;
    private Map<String, Object> map;
    private FasterJacksonObjectConverter converter;
    
    @BeforeEach
    public void before() {

        myObject = new MyObject();
        myObject.number = 123.4D;
        myObject.string = "123.4D";
        map = Maps.newLinkedHashMap();
        map.put("number", 432.1D);
        map.put("string", "432.1D");
        converter = FasterJacksonObjectConverter.typeConverter(MyObject.class);

    }
    
    @Test
    void testConvert_CustomObject() {

        Object actual = converter.apply(myObject);
        assertThat(actual, is(notNullValue()));
        assertThat(actual, is(instanceOf(MyObject.class)));
        
        MyObject actualInstance = (MyObject)actual;
        
        assertThat(actualInstance.getNumber(), is(123.4D));
        assertThat(actualInstance.getString(), is("123.4D"));
        assertThat(actualInstance, is(myObject));

    }
    
    @Test
    void testConvert_Map() {
        Object actual = converter.apply(map);
        assertThat(actual, is(notNullValue()));
        assertThat(actual, is(instanceOf(MyObject.class)));
        
        MyObject actualInstance = (MyObject)actual;
        
        assertThat(actualInstance.getNumber(), is(432.1D));
        assertThat(actualInstance.getString(), is("432.1D"));
        assertThat(actualInstance, not(is(myObject)));
    }
    
    @Data
    private static class MyObject {

        private Number number;
        private String string;

    }

}
