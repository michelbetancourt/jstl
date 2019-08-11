package org.jstlang.converters.fasterjackson;

import java.util.function.Function;

import javax.annotation.Nonnull;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@RequiredArgsConstructor(staticName = "typeConverter")
@Accessors(fluent = true)
@Setter
public class FasterJacksonObjectConverter implements Function<Object, Object> {

    private final Class<?> desiredType;

    private @Nonnull ObjectMapper mapper = FasterJacksonDefaultMapper.instance;

    @Override
    public Object apply(Object value) {
        if (null == desiredType) {
            return value;
        }

        return mapper.convertValue(value, desiredType);
    }

}
