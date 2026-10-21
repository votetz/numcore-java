package com.numcore.numeric.linearalgebra.gaussian.impl;

import com.numcore.numeric.linearalgebra.gaussian.GaussianSolver;
import com.numcore.numeric.linearalgebra.matrix.Matrix;
import com.numcore.numeric.linearalgebra.matrix.impl.RectangleArrayMatrix;
import com.numcore.numeric.linearalgebra.vector.Vector;
import com.numcore.numeric.linearalgebra.vector.impl.ArrayVector;

public class GaussianEliminationSolver implements GaussianSolver {
    @Override
    public Vector solve(Matrix coefficients, Vector rightHandSide) {
        checkSquare(coefficients);
        if (rightHandSide.size() != coefficients.rows()) {
            throw new IllegalArgumentException("Right-hand-side size must match matrix rows");
        }

        double[][] matrix = copyValues(coefficients);
        double[] values = new double[rightHandSide.size()];
        for (int row = 0; row < values.length; row++) {
            values[row] = rightHandSide.get(row);
        }

        if (forwardElimination(matrix, values) == 0) {
            throw new IllegalArgumentException("Matrix is singular");
        }

        double[] solution = new double[values.length];
        for (int row = solution.length - 1; row >= 0; row--) {
            double remainder = values[row];
            for (int column = row + 1; column < solution.length; column++) {
                remainder -= matrix[row][column] * solution[column];
            }
            solution[row] = remainder / matrix[row][row];
        }
        return new ArrayVector(solution);
    }

    @Override
    public Vector residual(Matrix coefficients, Vector solution, Vector rightHandSide) {
        if (coefficients.cols() != solution.size()
                || coefficients.rows() != rightHandSide.size()) {
            throw new IllegalArgumentException("Vector sizes must match matrix dimensions");
        }

        double[] residual = new double[coefficients.rows()];
        for (int row = 0; row < coefficients.rows(); row++) {
            double sum = 0;
            for (int column = 0; column < coefficients.cols(); column++) {
                sum += coefficients.get(row, column) * solution.get(column);
            }
            residual[row] = sum - rightHandSide.get(row);
        }
        return new ArrayVector(residual);
    }

    @Override
    public double determinant(Matrix coefficients) {
        checkSquare(coefficients);
        double[][] matrix = copyValues(coefficients);
        int sign = forwardElimination(matrix, null);
        if (sign == 0) {
            return 0;
        }

        double determinant = sign;
        for (int index = 0; index < matrix.length; index++) {
            determinant *= matrix[index][index];
        }
        return determinant;
    }

    @Override
    public Matrix inverse(Matrix coefficients) {
        checkSquare(coefficients);
        int size = coefficients.rows();
        double[][] inverse = new double[size][size];
        for (int column = 0; column < size; column++) {
            double[] unitVector = new double[size];
            unitVector[column] = 1;
            Vector solution = solve(coefficients, new ArrayVector(unitVector));
            for (int row = 0; row < size; row++) {
                inverse[row][column] = solution.get(row);
            }
        }
        return new RectangleArrayMatrix(inverse);
    }

    private static void checkSquare(Matrix matrix) {
        if (matrix.rows() == 0 || matrix.rows() != matrix.cols()) {
            throw new IllegalArgumentException("Matrix must be nonempty and square");
        }
    }

    private static double[][] copyValues(Matrix source) {
        double[][] copy = new double[source.rows()][source.cols()];
        for (int row = 0; row < source.rows(); row++) {
            for (int column = 0; column < source.cols(); column++) {
                copy[row][column] = source.get(row, column);
            }
        }
        return copy;
    }

    private static int forwardElimination(double[][] matrix, double[] rightHandSide) {
        int sign = 1;
        for (int pivot = 0; pivot < matrix.length; pivot++) {
            int selectedRow = pivot;
            for (int row = pivot + 1; row < matrix.length; row++) {
                if (Math.abs(matrix[row][pivot]) > Math.abs(matrix[selectedRow][pivot])) {
                    selectedRow = row;
                }
            }
            if (matrix[selectedRow][pivot] == 0) {
                return 0;
            }
            if (selectedRow != pivot) {
                double[] oldRow = matrix[pivot];
                matrix[pivot] = matrix[selectedRow];
                matrix[selectedRow] = oldRow;
                if (rightHandSide != null) {
                    double oldValue = rightHandSide[pivot];
                    rightHandSide[pivot] = rightHandSide[selectedRow];
                    rightHandSide[selectedRow] = oldValue;
                }
                sign = -sign;
            }

            for (int row = pivot + 1; row < matrix.length; row++) {
                double factor = matrix[row][pivot] / matrix[pivot][pivot];
                matrix[row][pivot] = 0;
                for (int column = pivot + 1; column < matrix.length; column++) {
                    matrix[row][column] -= factor * matrix[pivot][column];
                }
                if (rightHandSide != null) {
                    rightHandSide[row] -= factor * rightHandSide[pivot];
                }
            }
        }
        return sign;
    }
}
