package org.jstlang;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.jstlang.converters.fasterjackson.FasterJacksonSpecificObjectReader;
import org.jstlang.util.ObjectUnflattener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.function.Function;

import static com.jayway.jsonpath.Option.DEFAULT_PATH_LEAF_TO_NULL;
import static com.jayway.jsonpath.Option.SUPPRESS_EXCEPTIONS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;

public class ParserTest {

    Function<Object,Object> unflattener;
    private Function<Object, Map> objectDefConverter;
    private ObjectMapper mapper;
    private URL testResource;
    Map<String,Object> mappings;
    private Configuration jsonConfig = Configuration.builder()
            .options(DEFAULT_PATH_LEAF_TO_NULL, SUPPRESS_EXCEPTIONS).build();

    @BeforeEach
    public void setup() throws IOException {

        // setup a good standard set of properties for mapping
        mapper = new ObjectMapper(new YAMLFactory());
        mapper.registerModule(new AfterburnerModule());
        mapper.registerModule(new JavaTimeModule());
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, false);

        unflattener = ObjectUnflattener.getDefault();

        objectDefConverter = FasterJacksonSpecificObjectReader.typeConverter(Map.class)
                .mapper(mapper);

        testResource = Resources.getResource("new-spec-grouping.yml");

        mappings = (Map<String,Object>) unflattener.apply(objectDefConverter.apply(Resources.toString(testResource, Charsets.UTF_8)));


    }

    @Test
    public void testParser() {
        // Setup
        DocumentContext mappingsDocument = JsonPath.parse(mappings, jsonConfig);
        Map<String,Object> get1 = mappingsDocument.read("$.fields[0].get");
        Map<String,Object> get2 = mappingsDocument.read("$.fields[1].get");
        Map<String,Object> literalDotTest = mappingsDocument.read("$.fields[2]");

        // Validation
        assertThat(get1, hasEntry("path","$.collection"));
        assertThat(get2, hasEntry("path",null));
        // check that the escape character was removed
        assertThat(literalDotTest, hasEntry("get.path", "$.field"));
    }
}
