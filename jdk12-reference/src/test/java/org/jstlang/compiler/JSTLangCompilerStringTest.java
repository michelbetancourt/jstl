package org.jstlang.compiler;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.instanceOf;

import java.util.Map;
import java.util.function.Function;

import org.jstlang.domain.definition.ObjectDef;
import org.jstlang.parser.YamldParser;
import org.jstlang.util.ExtObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Maps;

class JSTLangCompilerStringTest {

    private ObjectDef objectDef;
    private JSTLangCompiler compiler;
    private Map<String, Object> sourceValues;
    private Map<String, Object> targetValues;

    @BeforeEach
    public void beforeEach() throws Exception {
        YamldParser<ObjectDef> parser = YamldParser.toType(ObjectDef.class);
        objectDef = parser.apply("spec-strings.yml");

        compiler = JSTLangCompiler.newInstance();

        sourceValues = Maps.newLinkedHashMap();
        sourceValues.put("stringKey", "AbCdEf");

        targetValues = Maps.newLinkedHashMap();

    }

    @Test
    void testCompile() {

        Function<Object, Object> compiled = compiler.compile(objectDef);
        Object actual = compiled.apply(sourceValues);

        assertThat(actual, instanceOf(ExtObject.class));
        targetValues = ((ExtObject)actual).getData();

        assertThat(targetValues, hasEntry("upperKey", "ABCDEF"));
        assertThat(targetValues, hasEntry("lowerKey", "abcdef"));
        
    }

}
