package org.jstlang.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import org.jstlang.compiler.JSTLangCompiler;
import org.jstlang.converters.fasterjackson.FasterJacksonObjectConverter;
import org.jstlang.domain.definition.ObjectDef;
import org.jstlang.domain.lambda.JstlLamdaInput;
import org.jstlang.util.ObjectUnflattener;

import java.util.function.Function;

public class JstlLambda {

    private final Function<Object, Object> converter = FasterJacksonObjectConverter.typeConverter(ObjectDef.class);
    private JSTLangCompiler compiler = JSTLangCompiler.newInstance();

    public Object jstlHandler(JstlLamdaInput lamdaInput, Context context){

        LambdaLogger logger = context.getLogger();

        logger.log(lamdaInput.toString());

        ObjectUnflattener unflattener = ObjectUnflattener.getDefault();
        // Get the mapping definition that will be used to transform the input
        ObjectDef mappingDefinitions = (ObjectDef) converter.apply(unflattener.apply(lamdaInput.getMappings()));
        // creates the function that will be used to apply the transformations
        Function<Object,Object> mappings = compiler.compile(mappingDefinitions);

        // apply the mappings
        Object result = mappings.apply(lamdaInput.getSourceObject()) ;

        return result;
    }
}
