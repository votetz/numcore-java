package com.numcore.numeric.linearalgebra.tridiagonal.impl;

import com.numcore.numeric.linearalgebra.tridiagonal.TridiagonalSolver;
import com.numcore.numeric.linearalgebra.tridiagonal.TridiagonalSystem;
import com.numcore.numeric.linearalgebra.vector.Vector;
import com.numcore.numeric.linearalgebra.vector.impl.ArrayVector;

public class ThomasAlgorithmSolver implements TridiagonalSolver {
    @Override
    public Vector solve(TridiagonalSystem system) {
        double[] lower = system.lowerDiagonal();
        double[] main = system.mainDiagonal();
        double[] upper = system.upperDiagonal();
        double[] rightHandSide = system.rightHandSide();
        int size = main.length;

        double[] alpha = new double[size];
        double[] beta = new double[size];
        calculateForwardSweep(lower, main, upper, rightHandSide, alpha, beta);

        double[] solution = calculateBackwardSweep(alpha, beta);
        return new ArrayVector(solution);
    }

    private void calculateForwardSweep(double[] lower, double[] main, double[] upper,
                                       double[] rightHandSide, double[] alpha, double[] beta) {
        checkDenominator(main[0], 0);
        alpha[0] = -upper[0] / main[0];
        beta[0] = rightHandSide[0] / main[0];

        for (int index = 1; index < main.length; index++) {
            double denominator = main[index] + lower[index] * alpha[index - 1];
            checkDenominator(denominator, index);

            alpha[index] = -upper[index] / denominator;
            beta[index] = (rightHandSide[index] - lower[index] * beta[index - 1])
                    / denominator;
        }
    }

    private double[] calculateBackwardSweep(double[] alpha, double[] beta) {
        double[] solution = new double[beta.length];
        solution[solution.length - 1] = beta[beta.length - 1];

        for (int index = solution.length - 2; index >= 0; index--) {
            solution[index] = alpha[index] * solution[index + 1] + beta[index];
        }
        return solution;
    }

    private void checkDenominator(double denominator, int index) {
        if (denominator == 0) {
            throw new IllegalArgumentException("Zero denominator at equation " + index);
        }
    }
}
