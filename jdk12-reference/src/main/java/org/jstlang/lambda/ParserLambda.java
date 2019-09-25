package org.jstlang.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.google.common.collect.Maps;
import org.jstlang.compiler.JSTLangCompiler;
import org.jstlang.domain.definition.ObjectDef;
import org.jstlang.parser.YamldParser;

import java.util.Map;
import java.util.Optional;

public class ParserLambda {

    public Map<String,Object> parserHandler(Map<String,Object> input, Context context){
//        Map<String,Object> sourceValues = Maps.newLinkedHashMap();
//        sourceValues.put("key", "123");
//        sourceValues.put("myString", "123");
//        sourceValues.put("someKey", "myValue");
//        sourceValues.put("unskippableIfEmptyKey", Maps.newLinkedHashMap());
//        sourceValues.put("keyNotRead", "this value is not picked up");
//
//        YamldParser<ObjectDef> parser = YamldParser.toType(ObjectDef.class);
//        ObjectDef objectDef = parser.apply("spec-keys.yml");
//        JSTLangCompiler compiler = JSTLangCompiler.newInstance();



        Optional.ofNullable(context)
                .map(Context::getLogger)
                .ifPresent(logger -> {
                    logger.log("Made it to the logger");
                    logger.log(input.toString());
                });
        Map<String,Object> map = Maps.newLinkedHashMap();
        Map<String,Object> body = Maps.newLinkedHashMap();
        body.put("key","the value");
        map.put("statusCode", 200);
        map.put("headers", Maps.newLinkedHashMap());
//        map.put("body", compiler.compile(objectDef).apply(sourceValues));
        map.put("body", body);
        map.put("isBase64Encoded", false);
        return map;
    }
}
