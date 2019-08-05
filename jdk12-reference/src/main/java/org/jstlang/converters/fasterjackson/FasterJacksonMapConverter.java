package org.jstlang.converters.fasterjackson;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.Nonnull;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "typeConverter")
public class FasterJacksonMapConverter implements Function<Object, Map<String, Object>> {
	
	static JavaType mapType = FasterJacksonObjectConverter.defaultMapper.getTypeFactory().constructMapType(LinkedHashMap.class,
			String.class, Object.class);
	
	private @Nonnull ObjectMapper mapper = FasterJacksonObjectConverter.defaultMapper;
	
	@Override
	public Map<String, Object> apply(Object value) {
		
		return mapper.convertValue(value, mapType);
	}
	
}
