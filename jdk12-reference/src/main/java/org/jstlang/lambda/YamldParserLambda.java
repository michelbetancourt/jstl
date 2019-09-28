package org.jstlang.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import org.jstlang.util.ObjectUnflattener;

import java.util.Map;

public class YamldParserLambda {

    public Object parserHandler(Map<String,Object> input, Context context){
        ObjectUnflattener unflattener = ObjectUnflattener.getDefault();

        return unflattener.apply(input);
    }
}
