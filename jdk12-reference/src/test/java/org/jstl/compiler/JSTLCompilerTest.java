package org.jstl.compiler;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasKey;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.jstl.domain.config.JSTLObject;
import org.jstl.domain.config.JSTLSource;
import org.jstl.domain.config.JSTLTarget;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

class JSTLCompilerTest {

	private List<JSTLObject> mappings;
	private JSTLCompiler compiler;
	private Map<String, Object> sourceValues;
	private Map<String, Object> nestedValues;
	private Map<String, Object> targetValues;
	
	
	@BeforeEach
	public void beforeEach() {
		
		mappings = Lists.newArrayList();
		mappings.add(JSTLObject.builder()
				.source(JSTLSource.builder()
						.path("$.key")
						.build())
				.target(JSTLTarget.builder()
						.path("$.newKey")
						.build())
				.build());
		mappings.add(JSTLObject.builder()
				.source(JSTLSource.builder()
						.path("$.nested.key")
						.build())
				.target(JSTLTarget.builder()
						.path("$.nested.newKey")
						.build())
				.build());
		
		compiler = JSTLCompiler.jstlCompiler(mappings);
		
		sourceValues = Maps.newLinkedHashMap();
		sourceValues.put("key", "123");
		
		nestedValues = Maps.newLinkedHashMap();
		sourceValues.put("nested", nestedValues);
		
		nestedValues.put("key", 123);
		
		targetValues = Maps.newLinkedHashMap();
		
		
	}
	
	@Test
	void testCompile() {
		Function<Map<String, Object>, Map<String, Object>> compiled = compiler.compile();
		targetValues = compiled.apply(sourceValues);
		
		assertThat(targetValues, hasEntry("newKey", "123"));
		assertThat(targetValues, hasKey("nested"));
		
		
	}

}
