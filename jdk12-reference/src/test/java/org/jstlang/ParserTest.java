package org.jstlang;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.jstlang.parser.YamldParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static com.jayway.jsonpath.Option.DEFAULT_PATH_LEAF_TO_NULL;
import static com.jayway.jsonpath.Option.SUPPRESS_EXCEPTIONS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;

public class ParserTest {
    Map<String,Object> mappings;
    private Configuration jsonConfig = Configuration.builder()
            .options(DEFAULT_PATH_LEAF_TO_NULL, SUPPRESS_EXCEPTIONS).build();

    @BeforeEach
    public void setup() throws IOException {

        YamldParser<Map> parser = YamldParser.toType(Map.class);
        mappings = (Map<String,Object>) parser.apply("new-spec-grouping.yml");


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
