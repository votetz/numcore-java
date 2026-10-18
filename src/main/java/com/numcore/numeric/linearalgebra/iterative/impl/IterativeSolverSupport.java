package com.numcore.numeric.linearalgebra.iterative.impl;

import com.numcore.numeric.linearalgebra.matrix.Matrix;
import com.numcore.numeric.linearalgebra.vector.Vector;

final class IterativeSolverSupport {
    private IterativeSolverSupport() {
    }

    static void validate(Matrix coefficients, Vector rightHandSide,
                         double epsilon, int maxIterations) {
        if (coefficients.rows() != coefficients.cols()) {
            throw new IllegalArgumentException("Coefficient matrix must be square");
        }
        if (rightHandSide.size() != coefficients.rows()) {
            throw new IllegalArgumentException("Right-hand-side size must match matrix rows");
        }
        if (!Double.isFinite(epsilon) || epsilon <= 0) {
            throw new IllegalArgumentException("Epsilon must be positive and finite");
        }
        if (maxIterations <= 0) {
            throw new IllegalArgumentException("Maximum iterations must be positive");
        }

        for (int i = 0; i < coefficients.rows(); i++) {
            if (coefficients.get(i, i) == 0) {
                throw new IllegalArgumentException("Zero diagonal element at index " + i);
            }
        }
    }

    static double maxDifference(Vector first, Vector second) {
        double maxDifference = 0;
        for (int i = 0; i < first.size(); i++) {
            double difference = Math.abs(first.get(i) - second.get(i));
            maxDifference = Math.max(maxDifference, difference);
        }
        return maxDifference;
    }
}
