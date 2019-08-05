package org.jstlang.compiler.target;

import java.util.function.Function;

import org.jstlang.compiler.converters.fasterjackson.FasterJacksonTypeConverter;
import org.jstlang.domain.config.TargetDef;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "defaultHandler")
public class TargetHandlerFactory implements Function<TargetDef, TargetHandler> {

	@Override
	public TargetHandler apply(TargetDef definition) {
		
		return TargetPathHandler.targetPath(definition.getPath())
				.typeConverter(FasterJacksonTypeConverter.typeConverter(definition.getType()));
	}

}
