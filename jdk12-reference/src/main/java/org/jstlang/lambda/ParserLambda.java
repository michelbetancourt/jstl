package org.jstlang.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import org.jstlang.util.ObjectUnflattener;

import java.util.Map;
import java.util.Optional;

public class ParserLambda {

    public Map<String,Object> parserHandler(Map<String,Object> input, Context context){
        ObjectUnflattener unflattener = ObjectUnflattener.getDefault();
        Map<String,Object> output = (Map<String,Object>) unflattener.apply(input);
        Optional.ofNullable(context)
                .map(Context::getLogger)
                .ifPresent(logger -> {
                    logger.log("Input: ");
                    logger.log(input.toString() + "\n");
                    logger.log("Output: ");
                    logger.log(output.toString() + "\n");
                });
        return output;
    }
}
