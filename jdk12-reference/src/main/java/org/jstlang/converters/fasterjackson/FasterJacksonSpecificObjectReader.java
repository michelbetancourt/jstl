package org.jstlang.converters.fasterjackson;

import java.io.IOException;
import java.util.function.Function;

import javax.annotation.Nonnull;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@RequiredArgsConstructor(staticName = "typeConverter")
@Accessors(fluent = true)
@Setter
public class FasterJacksonSpecificObjectReader<T> implements Function<Object, T> {

    private final @Nonnull Class<T> desiredType;

    private @Nonnull ObjectMapper mapper = FasterJacksonDefaultMapper.instance;

    @Override
    public T apply(@Nonnull Object value) {
        if (value instanceof byte[]) {
            return mapBytes((byte[]) value);
        }

        if (value instanceof String) {
            return mapBytes(((String) value).getBytes());
        }

        throw new UnsupportedOperationException("Unable to dynamically link");
    }

    private T mapBytes(byte[] bytes) {
        try {
            return mapper.readValue(bytes, desiredType);
        } catch (IOException e) {
            String errorMessage = String.format("Unable to parse data for,inputType=%s,desiredType=%s",
                    byte[].class.getCanonicalName(), desiredType.getCanonicalName());
            throw new IllegalStateException(errorMessage, e);
        }
    }

}
