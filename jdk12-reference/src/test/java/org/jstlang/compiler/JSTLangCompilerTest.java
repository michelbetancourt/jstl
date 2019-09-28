package org.jstlang.compiler;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.instanceOf;

import java.util.Map;
import java.util.function.Function;

import org.jstlang.domain.definition.ObjectDef;
import org.jstlang.parser.YamldParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Maps;
import com.jayway.jsonpath.JsonPath;

class JSTLangCompilerTest {

    private ObjectDef objectDef;
    private JSTLangCompiler compiler;
    private Map<String, Object> sourceValues;
    private Map<String, Object> sourceNestedValues;
    private Map<String, Object> targetValues;

    @BeforeEach
    public void beforeEach() throws Exception {
        YamldParser<ObjectDef> parser = YamldParser.toType(ObjectDef.class);

        objectDef = parser.apply("spec-keys.yml");
        compiler = JSTLangCompiler.newInstance();

        sourceValues = Maps.newLinkedHashMap();
        sourceValues.put("key", "123");
        sourceValues.put("myString", "123");
        sourceValues.put("someKey", "myValue");
        sourceValues.put("unskippableIfEmptyKey", Maps.newLinkedHashMap());
        sourceValues.put("keyNotRead", "this value is not picked up");

        sourceNestedValues = Maps.newLinkedHashMap();
        sourceValues.put("nested", sourceNestedValues);

        sourceNestedValues.put("key", 123);

    }

    @SuppressWarnings("unchecked")
    @Test
    void testCompile() {
        Function<Object, Object> compiled = compiler.compile(objectDef);
        Object actual = compiled.apply(sourceValues);

        assertThat(actual, instanceOf(Map.class));
        targetValues = (Map<String, Object>)actual;

        assertThat(targetValues, hasEntry("newKey", "123"));
        // check that the type transformation happened successfully
        assertThat(targetValues, hasEntry("myNumber", 123));
        assertThat(targetValues, hasEntry("myDouble", 123.0));
        // check nested objects were created
        assertThat(targetValues, hasKey("nested"));
        assertThat(JsonPath.read(targetValues, "$.nested.newKey"), is(123));
        assertThat(JsonPath.read(targetValues, "$.nested.someNewKey"), is("myValue"));

        // TODO: find a way to stop Json parser from treating empty values as if they were null
        // TODO: enable assertion below when above requirement is completed
//        assertThat(targetValues, hasEntry("unskippableIfNullKey", null));

        // verify null value is persisted when told not to skip
        // this checks that the ifEmpty check for skip fails when the value is null
        assertThat(targetValues, hasEntry("unskippableIfNullKey", null));
        // finally verify key not read is skipped
        assertThat(targetValues, not(hasKey("keyNotRead")));
        

    }

}
