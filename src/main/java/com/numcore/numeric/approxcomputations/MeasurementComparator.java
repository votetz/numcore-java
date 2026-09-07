package com.numcore.numeric.approxcomputations;

public class MeasurementComparator {
    private final double lengthInMeters;

    public MeasurementComparator(double lengthInMeters) {
        this.lengthInMeters = lengthInMeters;
    }

    public MeasurementComparator calculateRelativeError(double deltaMeters) {
        double relativeError = deltaMeters / Math.abs(this.lengthInMeters);
        return new MeasurementComparator(relativeError);
    }

    public double getValue() {
        return lengthInMeters;
    }
}