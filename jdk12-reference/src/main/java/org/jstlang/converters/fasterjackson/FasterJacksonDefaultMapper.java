package org.jstlang.converters.fasterjackson;

import java.util.LinkedHashMap;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;

public class FasterJacksonDefaultMapper {

    static final ObjectMapper instance = new ObjectMapper();
    static final JavaType mapOfObject = instance.getTypeFactory()
            .constructMapType(LinkedHashMap.class, String.class, Object.class);

    static {
        instance.registerModule(new AfterburnerModule());
        instance.registerModule(new JavaTimeModule());
        instance.setSerializationInclusion(Include.NON_NULL);
        instance.setSerializationInclusion(Include.NON_EMPTY);
        instance.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        instance.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        instance.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        instance.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, false);

    }
    
}
