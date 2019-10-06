package org.jstlang.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.not;

class AggregateOpsTest {

    List<Object> input;

    @BeforeEach
    public void beforeEach() {
        Map<String,Object> map1 = Maps.newLinkedHashMap();
        Map<String,Object> map2 = Maps.newLinkedHashMap();

        map1.put("total", 10);
        map1.put("amount", 10.2);

        map2.put("total", 20);
        map2.put("amount", 20.2);

        input = Lists.newArrayList(map1, map2);
    }

    @Test
    void sum() {
        Map<String,Object> result = (Map<String, Object>) AggregateOps.sum(input, Lists.newArrayList("total"));

        assertThat(result, hasEntry("total", BigDecimal.valueOf(30)));
        assertThat(result, not(hasEntry("amount", BigDecimal.valueOf(30.4))));

        result = (Map<String, Object>) AggregateOps.sum(input, Lists.newArrayList("total", "amount"));

        assertThat(result, hasEntry("total", BigDecimal.valueOf(30)));
        assertThat(result, hasEntry("amount", BigDecimal.valueOf(30.4)));
    }

    @Test
    void sum_NonNumbers() {
        Map<String,Object> map = Collections.singletonMap("nonNumber", "fish");
        Map<String,Object> map1 = Collections.singletonMap("nonNumber", "cow");
        input = Lists.newArrayList(map, map1);
        Map<String,Object> result = (Map<String, Object>) AggregateOps.sum(input, Lists.newArrayList("nonNumber"));

        assertThat(result, hasEntry("nonNumber", BigDecimal.ZERO));
    }

    @Test
    void sum_emptyInput() {
        Map<String,Object> map = Collections.emptyMap();
        input = Lists.newArrayList(map);
        Map<String,Object> result = (Map<String, Object>) AggregateOps.sum(input, Lists.newArrayList("someKey"));

        assertThat(result.isEmpty(), is(true));
    }

}