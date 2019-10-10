package org.jstlang.compiler.step;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jstlang.compiler.step.handler.StepHandler;
import org.jstlang.domain.definition.AggregatesDef;
import org.jstlang.domain.definition.step.AggregateStep;
import org.jstlang.domain.definition.step.StepDef;
import org.jstlang.util.AggregateOps;

import java.util.function.Function;

@RequiredArgsConstructor(staticName = "defaultHandler")
@Slf4j
public class AggregateDefStepFactory implements Function<StepDef, StepHandler> {

    @Override
    public StepHandler apply(StepDef stepDef) {
        AggregateStep aggregateStep = stepDef.getAggregate();
        if(aggregateStep.getFields() == null || aggregateStep.getOp() == null) {
            throw new IllegalArgumentException("Operation and fields cannot be null in Aggregation Step");
        }
        StepHandler handler = obj -> {
            log.info("Operation requested does not exist");
            return obj;
        };

        if(aggregateStep.getOp().equals(AggregatesDef.sum)) {
            handler = (context) -> AggregateOps.sum(context, aggregateStep.getFields());
        }

        return handler;
    }
}
