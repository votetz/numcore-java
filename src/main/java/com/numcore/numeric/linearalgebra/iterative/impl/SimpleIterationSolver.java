package com.numcore.numeric.linearalgebra.iterative.impl;

import com.numcore.numeric.linearalgebra.iterative.IterationResult;
import com.numcore.numeric.linearalgebra.iterative.IterativeSolver;
import com.numcore.numeric.linearalgebra.matrix.Matrix;
import com.numcore.numeric.linearalgebra.vector.Vector;
import com.numcore.numeric.linearalgebra.vector.impl.ArrayVector;

public class SimpleIterationSolver implements IterativeSolver {
    @Override
    public IterationResult solve(Matrix coefficients, Vector rightHandSide,
                                 double epsilon, int maxIterations) {
        IterativeSolverSupport.validate(coefficients, rightHandSide, epsilon, maxIterations);

        int size = coefficients.rows();
        Vector previous = new ArrayVector(size);
        Vector current = new ArrayVector(size);

        for (int iteration = 1; iteration <= maxIterations; iteration++) {
            calculateNextApproximation(coefficients, rightHandSide, previous, current);

            if (IterativeSolverSupport.maxDifference(previous, current) < epsilon) {
                return new IterationResult(current, iteration, true);
            }

            Vector oldPrevious = previous;
            previous = current;
            current = oldPrevious;
        }

        return new IterationResult(previous, maxIterations, false);
    }

    private void calculateNextApproximation(Matrix coefficients, Vector rightHandSide,
                                            Vector previous, Vector current) {
        for (int row = 0; row < coefficients.rows(); row++) {
            double sum = 0;
            for (int column = 0; column < coefficients.cols(); column++) {
                if (column != row) {
                    sum += coefficients.get(row, column) * previous.get(column);
                }
            }

            double diagonal = coefficients.get(row, row);
            current.set(row, (rightHandSide.get(row) - sum) / diagonal);
        }
    }
}
