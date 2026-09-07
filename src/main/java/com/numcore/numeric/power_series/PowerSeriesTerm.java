package com.numcore.numeric.power_series;

@FunctionalInterface
public interface PowerSeriesTerm {
    double compute(double x, int n);
}