package org.jstlang.compiler;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.annotation.Nonnull;

import org.jstlang.compiler.source.SourceHandler;
import org.jstlang.compiler.source.SourceHandlerFactory;
import org.jstlang.compiler.step.StepHandler;
import org.jstlang.compiler.step.StepHandlerFactory;
import org.jstlang.compiler.target.TargetHandler;
import org.jstlang.compiler.target.TargetHandlerFactory;
import org.jstlang.domain.config.ObjectDef;
import org.jstlang.domain.config.PathDef;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor(staticName = "newInstance")
@Setter
@Accessors(fluent = true)
@Slf4j
public class JSTLangCompiler {

	private @Nonnull SourceHandlerFactory sourceHandlerFactory = SourceHandlerFactory.defaultHandler();
	private @Nonnull StepHandlerFactory stepHandlerFactory = StepHandlerFactory.defaultHandler();
	private @Nonnull TargetHandlerFactory targetHandlerFactory = TargetHandlerFactory.defaultHandler();
	
	
	public Function<Object, Object> compile(@Nonnull ObjectDef jstlObject) {
		
		List<PathDef> definitions = jstlObject.getPaths();
		
		if(definitions.isEmpty()) {
			throw new IllegalArgumentException("No mappings to compile!");
		}
		
		Consumer<Document> func = doc -> {
			log.debug("Starting conversion,sourceDocument={},targetDocument={}", doc.getSourceObject(), doc.getTargetObject());
		};
		
		Iterator<PathDef> it = definitions.stream()
				.filter(Objects::nonNull)
				.iterator();
		
		while(it.hasNext()) {
			PathDef definition = it.next();
			SourceHandler sourceHandler = sourceHandlerFactory.apply(definition.getSource());
		    StepHandler stepHandler = stepHandlerFactory.apply(definition.getSteps());
			TargetHandler targetHandler = targetHandlerFactory.apply(definition.getTarget());
			func = func.andThen(SourceToTargetBinder.binder(sourceHandler, stepHandler, targetHandler));
		}
		
		return TargetMapBinder.defaultBinder(func);
	}
}
