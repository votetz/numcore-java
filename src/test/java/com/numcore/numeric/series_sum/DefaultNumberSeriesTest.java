package com.numcore.numeric.series_sum;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultNumberSeriesTest {

    @Test
    void shouldComputeSeriesWithTolerance() {
        NumberSeries series = new DefaultNumberSeries(0.25, 1, n -> (3.0 * n - 2) / (3.0 * n + 4));

        double result = series.compute(1e-6);

        assertEquals(0.333333, result, 1e-3);
    }

    @Test
    void shouldTrackIterations() {
        NumberSeries series = new DefaultNumberSeries(0.25, 1, n -> (3.0 * n - 2) / (3.0 * n + 4));

        series.compute(1e-3);

        assertTrue(series.countOfIterations() > 0);
    }

    private void assertTrue(boolean condition) {
        if (!condition) {
            throw new AssertionError("Expected true");
        }
    }
}
