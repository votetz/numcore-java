package com.numcore.numeric.series_sum;

import java.util.function.Function;

public class DefaultNumberSeries implements NumberSeries {

    private final double startValue;
    private final int startNumber;
    private final Function<Integer, Double> multiplier;

    private int countIterations = 0;

    public DefaultNumberSeries(double startValue, int startNumber, Function<Integer, Double> multiplier) {
        this.startValue = startValue;
        this.startNumber = startNumber;
        this.multiplier = multiplier;
    }

    @Override
    public int countOfIterations() {
        return countIterations;
    }

    @Override
    public double compute(double tolerance) {
        countIterations = 0;

        double currentTerm = startValue;
        double result = currentTerm;

        for (int n = startNumber; Math.abs(currentTerm) > tolerance; ++n) {
            currentTerm *= multiplier.apply(n);
            result += currentTerm;
            ++countIterations;
        }

        return result;
    }
}
