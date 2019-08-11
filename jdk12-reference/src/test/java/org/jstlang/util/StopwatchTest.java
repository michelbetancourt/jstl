package org.jstlang.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StopwatchTest {
    
    private Stopwatch watch;
    
    @BeforeEach
    public void beforeEach() {
        watch = Stopwatch.start();
    }

    @Test
    void testElapsedMillis() throws InterruptedException {
        Thread.sleep(1);
        double actual = watch.elapsedMillis();
        assertThat(actual, greaterThanOrEqualTo(1D));
    }

}
