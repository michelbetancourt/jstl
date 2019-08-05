package org.jstl.compiler;

import com.jayway.jsonpath.DocumentContext;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Document {

	private final DocumentContext sourceObject;
	private final DocumentContext targetObject;
	
}
