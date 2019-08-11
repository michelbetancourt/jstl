package org.jstlang.converters.fasterjackson;

import java.util.Map;
import java.util.function.Function;

import javax.annotation.Nonnull;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@RequiredArgsConstructor(staticName = "typeConverter")
@Accessors(fluent = true)
@Setter
public class FasterJacksonMapConverter implements Function<Object, Map<String, Object>> {

    private @Nonnull ObjectMapper mapper = FasterJacksonDefaultMapper.instance;
    private @Nonnull JavaType mapType = FasterJacksonDefaultMapper.mapOfObject;

    @Override
    public Map<String, Object> apply(Object value) {

        return mapper.convertValue(value, mapType);
    }

}
