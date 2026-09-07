package com.numcore.numeric.series_sum;

@FunctionalInterface
public interface TermFunction {
    double compute(int n);
}