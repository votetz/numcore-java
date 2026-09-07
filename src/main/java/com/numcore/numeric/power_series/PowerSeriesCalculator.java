package com.numcore.numeric.power_series;

public class PowerSeriesCalculator {
    private final PowerSeriesTerm termFunction;

    public PowerSeriesCalculator(PowerSeriesTerm termFunction) {
        this.termFunction = termFunction;
    }

    public double computeByTerms(double x, int n) {
        double sum = 0.0;
        for (int i = 0; i <= n; i++) {
            sum += termFunction.compute(x, i);
        }
        return sum;
    }

    public double computeByEpsilon(double x, double epsilon) {
        double sum = 0.0;
        int n = 0;
        while (true) {
            double term = termFunction.compute(x, n);
            sum += term;
            if (Math.abs(term) < epsilon) {
                break;
            }
            n++;
        }
        return sum;
    }
}