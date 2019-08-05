package org.jstlang.converters.fasterjackson;

import java.util.function.Function;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "typeConverter")
public class FasterJacksonTypeConverter implements Function<Object, Object> {
	
	private static final ObjectMapper mapper = new ObjectMapper(); 
	
	static {
		mapper.registerModule(new AfterburnerModule());
		mapper.registerModule(new JavaTimeModule());
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.setSerializationInclusion(Include.NON_EMPTY);
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, false);
		
	}
	
	private final Class<?> desiredType;

	@Override
	public Object apply(Object value) {
		if(null == desiredType) {
			return value;
		}
		
		return mapper.convertValue(value, desiredType);
	}
	
}
