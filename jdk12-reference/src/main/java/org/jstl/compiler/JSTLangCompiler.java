package org.jstl.compiler;

import java.util.Iterator;
import java.util.List;
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
import org.jstl.domain.config.JSTLPath;

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
	
	
	public Function<Object, Object> compile(@Nonnull JSTLObject jstlObject) {
		
		List<JSTLPath> definitions = jstlObject.getPaths();
		
		if(definitions.isEmpty()) {
			throw new IllegalArgumentException("No mappings to compile!");
		}
		
		Consumer<Document> func = doc -> {
			log.debug("Starting conversion,sourceDocument={},targetDocument={}", doc.getSourceObject(), doc.getTargetObject());
		};
		
		Iterator<JSTLPath> it = definitions.stream()
				.filter(Objects::nonNull)
				.iterator();
		
		while(it.hasNext()) {
			JSTLPath definition = it.next();
			SourceHandler sourceHandler = sourceHandlerFactory.apply(definition.getSource());
		    StepHandler stepHandler = stepHandlerFactory.apply(definition.getSteps());
			TargetHandler targetHandler = targetHandlerFactory.apply(definition.getTarget());
			func = func.andThen(SourceToTargetBinder.binder(sourceHandler, stepHandler, targetHandler));
		}
		
		return TargetMapBinder.defaultBinder(func);
	}
}
