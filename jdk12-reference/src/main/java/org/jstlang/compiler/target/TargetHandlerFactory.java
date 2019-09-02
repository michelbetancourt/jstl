package org.jstlang.compiler.target;

import java.util.function.Function;

import org.jstlang.converters.fasterjackson.FasterJacksonObjectConverter;
import org.jstlang.domain.definition.TargetDef;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "defaultHandler")
public class TargetHandlerFactory implements Function<TargetDef, TargetHandler> {

    @Override
    public TargetHandler apply(TargetDef definition) {

        return TargetPathHandler.targetPath(definition.getPath())
                .skipDef(definition.getSkip())
                .typeConverter(FasterJacksonObjectConverter.typeConverter(definition.getType()));
    }

}
