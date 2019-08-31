package org.jstlang.compiler;

import java.util.function.Consumer;
import java.util.function.Function;

import javax.annotation.Nonnull;

import org.jstlang.compiler.source.SourceHandler;
import org.jstlang.compiler.target.TargetHandler;

import lombok.RequiredArgsConstructor;
import org.jstlang.domain.definition.ValueSourceDef;

@RequiredArgsConstructor(staticName = "binder")
class SourceToTargetBinder implements Consumer<Documents> {

    private @Nonnull SourceHandler sourceHandler;
    private @Nonnull Function<Object, Object> stepHandler;
    private @Nonnull TargetHandler targetHandler;
    private @Nonnull ValueSourceDef sourceIs;

    @Override
    public void accept(Documents context) {
        final Object sourceValue;
        // if the sourceIs property is set to source, read from te source, else read from the target
        if(sourceIs.equals(ValueSourceDef.source)) {
            sourceValue = sourceHandler.apply(context.getSourceObject());
        }else {
            sourceValue = sourceHandler.apply(context.getTargetObject());
        }

        final Object value = stepHandler.apply(sourceValue);

        targetHandler.accept(value, context.getTargetObject());

    }

}
