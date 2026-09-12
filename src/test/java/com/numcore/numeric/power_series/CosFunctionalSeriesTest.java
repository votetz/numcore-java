package com.numcore.numeric.power_series;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CosFunctionalSeriesTest {

    private final CosFunctionalSeries series = new CosFunctionalSeries();

    @Test
    void shouldComputeCosZero() {
        double result = series.compute(0.0, 1e-10);

        assertEquals(1.0, result, 1e-10);
    }

    @Test
    void shouldComputeCosPiOver3() {
        double x = Math.PI / 3;
        double result = series.compute(x, 1e-10);

        assertEquals(Math.cos(x), result, 1e-6);
    }

    @Test
    void shouldComputeByTerms() {
        double x = 1.0;
        double result = series.computeByTerms(x, 10);

        assertEquals(Math.cos(x), result, 1e-6);
    }
}
