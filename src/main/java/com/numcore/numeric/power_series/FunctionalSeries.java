package com.numcore.numeric.power_series;

public interface FunctionalSeries {

    int countOfIterations();

    double compute(double x, double tolerance);
}
