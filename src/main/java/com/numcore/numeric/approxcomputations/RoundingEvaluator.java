package com.numcore.numeric.approxcomputations;

public class RoundingEvaluator implements ApproximateComputation {
    private final double value;

    public RoundingEvaluator(double value) {
        this.value = value;
    }

    public RoundingEvaluator round(int decimals) {
        double factor = Math.pow(10, decimals);
        double roundedValue = Math.round(value * factor) / factor;
        return new RoundingEvaluator(roundedValue);
    }

    public RoundingEvaluator getAbsoluteError(RoundingEvaluator other) {
        double absoluteError = Math.abs(this.value - other.value);
        return new RoundingEvaluator(absoluteError);
    }

    public RoundingEvaluator getRelativePercent(RoundingEvaluator rounded) {
        double relativePercent = Math.abs(this.value - rounded.value) / Math.abs(this.value) * 100;
        return new RoundingEvaluator(relativePercent);
    }

    @Override
    public double getValue() {
        return value;
    }
}