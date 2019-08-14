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
public class FasterJacksonSpecificObjectConverter<T> implements Function<Object, T> {

    private final Class<T> desiredType;

    private @Nonnull ObjectMapper mapper = FasterJacksonDefaultMapper.instance;

    @Override
    public T apply(Object value) {
        return mapper.convertValue(value, desiredType);
    }

}
