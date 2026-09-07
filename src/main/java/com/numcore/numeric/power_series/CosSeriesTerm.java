package com.numcore.numeric.power_series;

public class CosSeriesTerm implements PowerSeriesTerm {

    @Override
    public double compute(double x, int n) {
        double sign = Math.pow(-1, n);
        double numerator = Math.pow(x, 2 * n);
        double denominator = factorial(2 * n);
        return sign * numerator / denominator;
    }

    private double factorial(int n) {
        double result = 1.0;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }
}