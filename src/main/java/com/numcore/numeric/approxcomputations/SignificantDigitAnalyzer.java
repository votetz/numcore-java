package com.numcore.numeric.approxcomputations;

public class SignificantDigitAnalyzer {
    private final double value;

    public SignificantDigitAnalyzer(double value) {
        this.value = value;
    }

    public int countSignificantDigits(double deltaX) {
        double absoluteX = Math.abs(this.value);
        if (absoluteX == 0 || deltaX <= 0) return 0;

        int m = (int) Math.floor(Math.log10(absoluteX));
        int k = (int) Math.floor(Math.log10(2 * deltaX));

        int n = m - k + 1;
        return Math.max(0, n);
    }

    public double getValue() {
        return value;
    }
}