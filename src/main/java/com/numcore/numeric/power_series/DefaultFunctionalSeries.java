package com.numcore.numeric.power_series;

import java.util.function.BiFunction;
import java.util.function.Function;

public class DefaultFunctionalSeries implements FunctionalSeries {

    private final int startNumber;
    private final Function<Double, Double> startEvaluator;
    private final BiFunction<Double, Integer, Double> multiplier;

    private int countIterations = 0;

    public DefaultFunctionalSeries(int startNumber, Function<Double, Double> startEvaluator,
                                   BiFunction<Double, Integer, Double> multiplier) {
        this.startNumber = startNumber;
        this.startEvaluator = startEvaluator;
        this.multiplier = multiplier;
    }

    @Override
    public int countOfIterations() {
        return countIterations;
    }

    @Override
    public double compute(double x, double tolerance) {
        countIterations = 0;

        double currentTerm = startEvaluator.apply(x);
        double result = currentTerm;
        double previous;

        for (int n = startNumber; ; ++n) {
            currentTerm *= multiplier.apply(x, n);
            previous = result;
            result += currentTerm;

            if (Math.abs(result - previous) < tolerance) {
                break;
            }
            ++countIterations;
        }

        return result;
    }

    public double computeByTerms(double x, int n) {
        double currentTerm = startEvaluator.apply(x);
        double result = currentTerm;

        for (int i = startNumber; i < n; ++i) {
            currentTerm *= multiplier.apply(x, i);
            result += currentTerm;
        }

        return result;
    }
}
