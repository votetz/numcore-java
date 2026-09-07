package com.numcore.numeric.series_sum;

public class SeriesSumCalculator {

    private final TermFunction termFunction;

    public SeriesSumCalculator(TermFunction termFunction) {
        this.termFunction = termFunction;
    }

    public double compute(double epsilon) {
        double sum = 0.0;
        int n = 1;
        while (true) {
            double term = termFunction.compute(n);
            sum += term;
            if (Math.abs(term) < epsilon) {
                break;
            }
            n++;
        }
        return sum;
    }
}