package org.jstlang.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import org.jstlang.compiler.JSTLangCompiler;
import org.jstlang.converters.fasterjackson.FasterJacksonObjectConverter;
import org.jstlang.domain.definition.ObjectDef;
import org.jstlang.domain.lambda.JstlLamdaInput;
import org.jstlang.util.ObjectUnflattener;

import java.util.function.Function;

public class ParserLambda {

    private final Function<Object, Object> converter = FasterJacksonObjectConverter.typeConverter(ObjectDef.class);
    private JSTLangCompiler compiler = JSTLangCompiler.newInstance();

    public Object parserHandler(JstlLamdaInput lamdaInput, Context context){
        ObjectUnflattener unflattener = ObjectUnflattener.getDefault();
        ObjectDef mappingDefinitions = (ObjectDef) converter.apply(unflattener.apply(lamdaInput.getMappings()));
        Function<Object,Object> mappings = compiler.compile(mappingDefinitions);

        Object result = mappings.apply(lamdaInput.getSourceObject()) ;

        return result;
    }
}
