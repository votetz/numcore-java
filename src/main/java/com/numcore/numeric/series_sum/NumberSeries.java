package com.numcore.numeric.series_sum;

public interface NumberSeries {

    int countOfIterations();

    double compute(double tolerance);
}
