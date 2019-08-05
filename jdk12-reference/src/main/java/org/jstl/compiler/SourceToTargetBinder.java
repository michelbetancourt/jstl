package org.jstl.compiler;

import java.util.function.Consumer;
import java.util.function.Function;

import javax.annotation.Nonnull;

import org.jstl.compiler.source.SourceHandler;
import org.jstl.compiler.target.TargetHandler;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "binder")
class SourceToTargetBinder implements Consumer<Document> {

	private @Nonnull SourceHandler sourceHandler;
	private @Nonnull Function<Object, Object> stepHandler;
	private @Nonnull TargetHandler targetHandler;
	
	@Override
	public void accept(Document context) {
		
		final Object sourceValue = sourceHandler.apply(context.getSourceObject());
		
		final Object value = stepHandler.apply(sourceValue);
		
		targetHandler.accept(value, context.getTargetObject());
		
	}

}
