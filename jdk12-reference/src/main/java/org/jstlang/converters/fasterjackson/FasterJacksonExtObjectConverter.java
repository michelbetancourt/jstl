package org.jstlang.converters.fasterjackson;

import java.util.function.Function;

import javax.annotation.Nonnull;

import org.jstlang.util.ExtObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "typeConverter")
public class FasterJacksonExtObjectConverter implements Function<Object, ExtObject> {

    private @Nonnull ObjectMapper mapper = FasterJacksonObjectConverter.defaultMapper;

    @Override
    public ExtObject apply(Object value) {

        return mapper.convertValue(value, ExtObject.class);
    }

}
