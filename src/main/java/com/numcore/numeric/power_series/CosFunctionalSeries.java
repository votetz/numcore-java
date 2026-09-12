package com.numcore.numeric.power_series;

public class CosFunctionalSeries extends DefaultFunctionalSeries {

    public CosFunctionalSeries() {
        super(0, x -> 1.0, (x, n) -> -x * x / ((2.0 * n + 1) * (2.0 * n + 2)));
    }
}
