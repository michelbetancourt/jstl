package org.jstlang.converters.fasterjackson;

import java.util.function.Function;

import javax.annotation.Nonnull;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "typeConverter")
public class FasterJacksonObjectConverter implements Function<Object, Object> {

    static final ObjectMapper defaultMapper = new ObjectMapper();

    static {
        defaultMapper.registerModule(new AfterburnerModule());
        defaultMapper.registerModule(new JavaTimeModule());
        defaultMapper.setSerializationInclusion(Include.NON_NULL);
        defaultMapper.setSerializationInclusion(Include.NON_EMPTY);
        defaultMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        defaultMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        defaultMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        defaultMapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, false);

    }

    private final Class<?> desiredType;

    private @Nonnull ObjectMapper mapper = defaultMapper;

    @Override
    public Object apply(Object value) {
        if (null == desiredType) {
            return value;
        }

        return mapper.convertValue(value, desiredType);
    }

}
