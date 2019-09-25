package org.jstlang;

import com.google.common.collect.Maps;
import org.jstlang.lambda.ParserLambda;
import org.junit.jupiter.api.Test;

public class HandlerTest {
    @Test
    public void test() {
        ParserLambda l = new ParserLambda();
        Object o = l.parserHandler(Maps.newHashMap(), null);
        System.out.println(o);
    }
}
