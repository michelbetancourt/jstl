package org.jstl.compiler.target;

import java.util.function.Function;

import org.jstl.compiler.TypeConverter;
import org.jstl.domain.config.JSTLTarget;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "defaultHandler")
public class TargetHandlerFactory implements Function<JSTLTarget, TargetHandler> {

	@Override
	public TargetHandler apply(JSTLTarget definition) {
		
		return TargetPathHandler.targetPath(definition.getPath(), TypeConverter.typeConverter(definition.getType()));
	}

}
