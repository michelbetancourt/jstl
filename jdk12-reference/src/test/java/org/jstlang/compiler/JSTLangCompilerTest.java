package org.jstlang.compiler;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.instanceOf;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.jstlang.converters.fasterjackson.FasterJacksonSpecificObjectReader;
import org.jstlang.domain.definition.ObjectDef;
import org.jstlang.domain.definition.FieldPathDef;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.io.Resources;
import com.jayway.jsonpath.JsonPath;

class JSTLangCompilerTest {

    private ObjectDef objectDef;
    private List<FieldPathDef> pathDefs;
    private JSTLangCompiler compiler;
    private Map<String, Object> sourceValues;
    private Map<String, Object> sourceNestedValues;
    private Map<String, Object> targetValues;
    private Function<Object, ObjectDef> objectDefConverter;
    private ObjectMapper mapper;
    private URL testResource;

    @BeforeEach
    public void beforeEach() throws Exception {
        
        // setup a good standard set of properties for mapping
        mapper = new ObjectMapper(new YAMLFactory());
        mapper.registerModule(new AfterburnerModule());
        mapper.registerModule(new JavaTimeModule());
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.setSerializationInclusion(Include.NON_EMPTY);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, false);
        
        objectDefConverter = FasterJacksonSpecificObjectReader.typeConverter(ObjectDef.class)
                .mapper(mapper);
        
        testResource = Resources.getResource("spec-example.yml");
        
        objectDef = objectDefConverter.apply(Resources.toString(testResource, Charsets.UTF_8));

//        objectDef = new ObjectDef();
//
//        pathDefs = Lists.newArrayList();
//        objectDef.setFieldPaths(pathDefs);
//
//        pathDefs.add(PathDef.builder()
//                            .source(SourceDef.builder()
//                                             .path("$.key")
//                                             .build())
//                            .target(TargetDef.builder()
//                                             .path("$.newKey")
//                                             .build())
//                            .build());
//        pathDefs.add(PathDef.builder()
//                            .source(SourceDef.builder()
//                                             .path("$.nested.key")
//                                             .build())
//                            .target(TargetDef.builder()
//                                             .path("$.nested.newKey")
//                                             .build())
//                            .build());
//        pathDefs.add(PathDef.builder()
//                            .source(SourceDef.builder()
//                                             .path("$.stringKey")
//                                             .build())
//                            .steps(Collections.singletonList(StepDef.builder()
//                                                                    .stringCase(StringCaseDef.upper)
//                                                                    .build()))
//                            .target(TargetDef.builder()
//                                             .path("$.upperKey")
//                                             .build())
//                            .build());
//        pathDefs.add(PathDef.builder()
//                            .source(SourceDef.builder()
//                                             .path("$.stringKey")
//                                             .build())
//                            .steps(Collections.singletonList(StepDef.builder()
//                                                                    .stringCase(StringCaseDef.lower)
//                                                                    .build()))
//                            .target(TargetDef.builder()
//                                             .path("$.lowerKey")
//                                             .build())
//                            .build());

        compiler = JSTLangCompiler.newInstance();

        sourceValues = Maps.newLinkedHashMap();
        sourceValues.put("key", "123");
        sourceValues.put("stringKey", "AbCdEf");
        sourceValues.put("keyNotRead", "this value is not picked up");

        sourceNestedValues = Maps.newLinkedHashMap();
        sourceValues.put("nested", sourceNestedValues);

        sourceNestedValues.put("key", 123);

        targetValues = Maps.newLinkedHashMap();

    }

    @SuppressWarnings("unchecked")
    @Test
    void testCompile() {
        Function<Object, Object> compiled = compiler.compile(objectDef);
        Object actual = compiled.apply(sourceValues);

        assertThat(actual, instanceOf(Map.class));
        targetValues = (Map<String, Object>) actual;

        assertThat(targetValues, hasEntry("newKey", "123"));
        assertThat(targetValues, hasKey("nested"));
        assertThat(JsonPath.read(targetValues, "$.nested.newKey"), is(123));

        assertThat(targetValues, hasEntry("upperKey", "ABCDEF"));
        assertThat(targetValues, hasEntry("lowerKey", "abcdef"));
        
        // finally verify key not read is skipped
        assertThat(targetValues, not(hasKey("keyNotRead")));
        

    }

}
