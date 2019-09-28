package org.jstlang.parser;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.jstlang.converters.fasterjackson.FasterJacksonSpecificObjectReader;
import org.jstlang.util.ObjectUnflattener;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.function.Function;

@RequiredArgsConstructor(staticName = "toType")
@Accessors(fluent = true)
@Setter
@Slf4j
public class YamldParser<T> implements Function<String,T> {

    private final @Nonnull Class<T> desiredType;
    private @Nonnull ObjectUnflattener unflattener = ObjectUnflattener.getDefault();
    private Function<Object,Map> mapConverter = FasterJacksonSpecificObjectReader.typeConverter(Map .class)
            .mapper(YamldParser.mapper);

    public static ObjectMapper mapper;
    static {
        mapper = new ObjectMapper(new YAMLFactory());
        mapper.registerModule(new AfterburnerModule());
        mapper.registerModule(new JavaTimeModule());
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, false);
    }

    @Override
    public T apply(String fileName) {
        URL resource = Resources.getResource(fileName);
        Object unflattenedMappings = null;
        try {
            unflattenedMappings = unflattener.apply(mapConverter.apply(Resources.toString(resource, Charsets.UTF_8)));
        } catch (IOException e) {
            String errorMessage = String.format("Unable to read file contents,fileName=%s", fileName);
            throw new RuntimeException(errorMessage,e);
        }
        return mapper.convertValue(unflattenedMappings, desiredType);
    }
}
