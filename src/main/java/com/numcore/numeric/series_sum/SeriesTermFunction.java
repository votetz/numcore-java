package com.numcore.numeric.series_sum;

public class SeriesTermFunction {

    public static TermFunction term() {
        return n -> 1.0 / ((3 * n - 2) * (3 * n + 1));
    }
}