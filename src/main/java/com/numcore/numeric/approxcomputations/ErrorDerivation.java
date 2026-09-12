package com.numcore.numeric.approxcomputations;

public class ErrorDerivation implements ApproximateComputation {
    private final double value;

    public ErrorDerivation(double value) {
        this.value = value;
    }

    public ErrorDerivation calculatorAbsoluteFromRelative(double a, double relativeDelta) {
        relativeDelta = Math.abs(a) * relativeDelta / 100.0;
        return new ErrorDerivation(relativeDelta);
    }

    @Override
    public double getValue() {
        return value;
    }
}