package org.jstlang.converters.fasterjackson;

import java.util.function.Function;

import javax.annotation.Nonnull;

import org.jstlang.util.ExtObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@RequiredArgsConstructor(staticName = "typeConverter")
@Accessors(fluent = true)
@Setter
public class FasterJacksonExtObjectConverter implements Function<Object, ExtObject> {

    private @Nonnull ObjectMapper mapper = FasterJacksonDefaultMapper.instance;

    @Override
    public ExtObject apply(Object value) {

        return mapper.convertValue(value, ExtObject.class);
    }

}
