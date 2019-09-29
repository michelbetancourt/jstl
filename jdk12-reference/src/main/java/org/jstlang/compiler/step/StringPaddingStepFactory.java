package org.jstlang.compiler.step;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jstlang.compiler.step.handler.StepHandler;
import org.jstlang.domain.definition.step.CharPaddingDef;
import org.jstlang.domain.definition.step.StepDef;
import org.jstlang.util.StringOps;

import java.util.function.Function;

@RequiredArgsConstructor(staticName = "defaultHandler")
@Accessors(fluent = true)
@Setter
public class StringPaddingStepFactory implements Function<StepDef, StepHandler> {

    public static String LEFT = "left";
    public static String RIGHT = "right";

    @Override
    public StepHandler apply(StepDef stepDef) {
        CharPaddingDef def = stepDef.getCharPadding();

        // validate that the steps requirements for execution are met
        if(def.getLeft() == null && def.getRight() == null) {
            throw new IllegalArgumentException("left and right properties cannot be null in Padding Step");
        } else if (def.getRight() != null && def.getLeft() != null) {
            throw new IllegalArgumentException("only one property (left or right) can be set at a time in Padding Step");
        }else if(def.getLimit() == null) {
            throw new IllegalArgumentException("limit property cannot be null in Padding Step");
        }

        String paddingType;
        Character value;
        if(def.getLeft() != null) {
            paddingType = StringPaddingStepFactory.LEFT;
            value = def.getLeft();
        } else {
            paddingType = StringPaddingStepFactory.RIGHT;
            value = def.getRight();
        }

        // returns a function that takes an input and will apply padding based on the properties set in this step
        return input -> StringOps.padding(input, paddingType, value, def.getLimit());
    }
}
