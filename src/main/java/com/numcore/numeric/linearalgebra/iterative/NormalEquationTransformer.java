package com.numcore.numeric.linearalgebra.iterative;

import com.numcore.numeric.linearalgebra.matrix.Matrix;
import com.numcore.numeric.linearalgebra.matrix.impl.RectangleArrayMatrix;
import com.numcore.numeric.linearalgebra.vector.Vector;
import com.numcore.numeric.linearalgebra.vector.impl.ArrayVector;

public final class NormalEquationTransformer {
    private NormalEquationTransformer() {
    }

    public static LinearSystem transform(Matrix coefficients, Vector rightHandSide) {
        if (coefficients.rows() != rightHandSide.size()) {
            throw new IllegalArgumentException("Right-hand-side size must match matrix rows");
        }

        int size = coefficients.cols();
        double[][] normalCoefficients = new double[size][size];
        double[] normalRightHandSide = new double[size];

        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                normalCoefficients[row][column] = scalarProductOfColumns(
                        coefficients, row, column);
            }
            normalRightHandSide[row] = scalarProductOfColumnAndVector(
                    coefficients, rightHandSide, row);
        }

        return new LinearSystem(
                new RectangleArrayMatrix(normalCoefficients),
                new ArrayVector(normalRightHandSide));
    }

    private static double scalarProductOfColumns(Matrix matrix, int firstColumn, int secondColumn) {
        double result = 0;
        for (int row = 0; row < matrix.rows(); row++) {
            result += matrix.get(row, firstColumn) * matrix.get(row, secondColumn);
        }
        return result;
    }

    private static double scalarProductOfColumnAndVector(Matrix matrix, Vector vector, int column) {
        double result = 0;
        for (int row = 0; row < matrix.rows(); row++) {
            result += matrix.get(row, column) * vector.get(row);
        }
        return result;
    }
}
