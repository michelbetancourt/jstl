package org.jstl.compiler;

import static com.jayway.jsonpath.Option.DEFAULT_PATH_LEAF_TO_NULL;
import static com.jayway.jsonpath.Option.SUPPRESS_EXCEPTIONS;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.annotation.Nonnull;

import com.google.common.collect.Maps;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Accessors(fluent = true)
@RequiredArgsConstructor(staticName = "defaultBinder")
@Slf4j
public class TargetMapBinder implements Function<Map<String, Object>, Map<String, Object>> {
	
	private @Nonnull Consumer<Document> binder;
	
	private @Nonnull Configuration jsonConfig = Configuration.builder()
			.options(DEFAULT_PATH_LEAF_TO_NULL, SUPPRESS_EXCEPTIONS)
			.build();
	private int totalBindings = -1;
	
	

	@Override
	public Map<String, Object> apply(Map<String, Object> sourceObject) {
		
		log.debug("About to start binding,totalBindings={}", totalBindings);
		Stopwatch timer = Stopwatch.start();
		final Map<String, Object> targetMap = Maps.newLinkedHashMap();
		
		try {
			DocumentContext read = JsonPath.parse(sourceObject, jsonConfig);
			DocumentContext write = JsonPath.parse(targetMap, jsonConfig);
			
			binder.accept(Document.builder()
					.sourceObject(read)
					.targetObject(write)
					.build());
		} catch(RuntimeException e) {
			log.error("Error caught during source to target bindings,totalBindings={},durationMillis={}", totalBindings, timer.elapsedMillis(), e);
			throw e;
		}
		
		log.info("Done processing bindings,totalBindings={},targetMapKeySize={},durationMillis={}", totalBindings, targetMap.keySet().size(), timer.elapsedMillis());
		
		
		return targetMap;
	}

}
