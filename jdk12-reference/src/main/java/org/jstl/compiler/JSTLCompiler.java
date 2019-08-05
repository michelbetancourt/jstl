package org.jstl.compiler;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.annotation.Nonnull;

import org.jstl.compiler.source.SourceHandler;
import org.jstl.compiler.source.SourceHandlerFactory;
import org.jstl.compiler.step.StepHandler;
import org.jstl.compiler.step.StepHandlerFactory;
import org.jstl.compiler.target.TargetHandler;
import org.jstl.compiler.target.TargetHandlerFactory;
import org.jstl.domain.config.JSTLObject;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor(staticName = "jstlCompiler")
@Setter
@Accessors(fluent = true)
@Slf4j
public class JSTLCompiler {

	private final @Nonnull List<JSTLObject> definitions;
	
	private @Nonnull SourceHandlerFactory sourceHandlerFactory = SourceHandlerFactory.defaultHandler();
	private @Nonnull StepHandlerFactory stepHandlerFactory = StepHandlerFactory.defaultHandler();
	private @Nonnull TargetHandlerFactory targetHandlerFactory = TargetHandlerFactory.defaultHandler();
	
	
	public Function<Map<String, Object>, Map<String, Object>> compile() {
		
		if(definitions.isEmpty()) {
			throw new IllegalArgumentException("No mappings to compile!");
		}
		
		Consumer<Document> func = doc -> {
			log.debug("Starting conversion,sourceDocument={},targetDocument={}", doc.getSourceObject(), doc.getTargetObject());
		};
		
		Iterator<JSTLObject> it = definitions.stream()
				.filter(Objects::nonNull)
				.iterator();
		
		while(it.hasNext()) {
			JSTLObject definition = it.next();
			SourceHandler sourceHandler = sourceHandlerFactory.apply(definition.getSource());
		    StepHandler stepHandler = stepHandlerFactory.apply(definition.getSteps());
			TargetHandler targetHandler = targetHandlerFactory.apply(definition.getTarget());
			func = func.andThen(SourceToTargetBinder.binder(sourceHandler, stepHandler, targetHandler));
		}
		
		return TargetMapBinder.defaultBinder(func);
	}
}
