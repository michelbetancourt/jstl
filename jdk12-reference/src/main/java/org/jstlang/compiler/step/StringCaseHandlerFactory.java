package org.jstlang.compiler.step;

import java.util.Map;
import java.util.function.Function;

import javax.annotation.Nonnull;

import org.jstlang.compiler.step.handler.StepHandler;
import org.jstlang.domain.definition.step.StepDef;
import org.jstlang.domain.definition.step.StringCaseDef;
import org.jstlang.util.StringCasing;

import com.google.common.collect.Maps;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@RequiredArgsConstructor(staticName = "defaultHandler")
@Accessors(fluent = true)
@Setter
public class StringCaseHandlerFactory implements Function<StepDef, StepHandler> {
    
    static final Map<StringCaseDef, StepHandler> defaultSteps = Maps.newLinkedHashMap();
    
    static {
        defaultSteps.put(StringCaseDef.lower, StringCasing::toLower);
        defaultSteps.put(StringCaseDef.upper, StringCasing::toUpper);
    }
    
    private Map<StringCaseDef, StepHandler> steps = defaultSteps;

    @Override
    public StepHandler apply(final @Nonnull StepDef stepDef) {
        StringCaseDef stringCase = stepDef.getStringCase();
        
        return steps.get(stringCase);
    }

}
