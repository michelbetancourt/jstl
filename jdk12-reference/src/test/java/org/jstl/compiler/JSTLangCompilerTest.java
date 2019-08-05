package org.jstl.compiler;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.instanceOf;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.jstl.domain.config.JSTLObject;
import org.jstl.domain.config.JSTLPath;
import org.jstl.domain.config.JSTLSource;
import org.jstl.domain.config.JSTLTarget;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jayway.jsonpath.JsonPath;

class JSTLangCompilerTest {

	private JSTLObject objectDef;
	private List<JSTLPath> pathDefs;
	private JSTLangCompiler compiler;
	private Map<String, Object> sourceValues;
	private Map<String, Object> nestedValues;
	private Map<String, Object> targetValues;
	
	
	@BeforeEach
	public void beforeEach() {
		
		objectDef = new JSTLObject();
		
		pathDefs = Lists.newArrayList();
		objectDef.setPaths(pathDefs);
		
		pathDefs.add(JSTLPath.builder()
				.source(JSTLSource.builder()
						.path("$.key")
						.build())
				.target(JSTLTarget.builder()
						.path("$.newKey")
						.build())
				.build());
		pathDefs.add(JSTLPath.builder()
				.source(JSTLSource.builder()
						.path("$.nested.key")
						.build())
				.target(JSTLTarget.builder()
						.path("$.nested.newKey")
						.build())
				.build());
		
		compiler = JSTLangCompiler.newInstance();
		
		sourceValues = Maps.newLinkedHashMap();
		sourceValues.put("key", "123");
		
		nestedValues = Maps.newLinkedHashMap();
		sourceValues.put("nested", nestedValues);
		
		nestedValues.put("key", 123);
		
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
		
	}

}
