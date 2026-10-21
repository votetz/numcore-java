package com.numcore.numeric.linearalgebra.gaussian;

import com.numcore.numeric.linearalgebra.gaussian.impl.GaussianEliminationSolver;
import com.numcore.numeric.linearalgebra.matrix.Matrix;
import com.numcore.numeric.linearalgebra.matrix.impl.RectangleArrayMatrix;
import com.numcore.numeric.linearalgebra.vector.Vector;
import com.numcore.numeric.linearalgebra.vector.impl.ArrayVector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GaussianEliminationSolverTest {
    private final GaussianSolver solver = new GaussianEliminationSolver();

    @Test
    void solvesVariantSevenAndCalculatesResidualDeterminantAndInverse() {
        Matrix matrix = variantSevenMatrix();
        Vector rightHandSide = new ArrayVector(new double[]{-30.8114, 49.4996, 14.7635, -32.5864});

        Vector solution = solver.solve(matrix, rightHandSide);

        double[] expected = {0.175631285267, 0.254007539523,
                2.040380527450, -1.857948654661};
        for (int index = 0; index < expected.length; index++) {
            assertEquals(expected[index], solution.get(index), 1e-9);
        }

        Vector residual = solver.residual(matrix, solution, rightHandSide);
        for (int index = 0; index < residual.size(); index++) {
            assertEquals(0.0, residual.get(index), 1e-10);
        }
        assertEquals(2622717.32640549, solver.determinant(matrix), 1e-5);

        Matrix inverse = solver.inverse(matrix);
        for (int row = 0; row < matrix.rows(); row++) {
            for (int column = 0; column < matrix.cols(); column++) {
                double product = 0;
                for (int index = 0; index < matrix.cols(); index++) {
                    product += matrix.get(row, index) * inverse.get(index, column);
                }
                assertEquals(row == column ? 1.0 : 0.0, product, 1e-10);
            }
        }

        assertEquals(22.5575, matrix.get(0, 0));
        assertEquals(-41.8493, matrix.get(3, 0));
        assertEquals(-30.8114, rightHandSide.get(0));
    }

    @Test
    void swapsRowsWhenTheFirstPivotIsZero() {
        Matrix matrix = new RectangleArrayMatrix(new double[][]{{0, 2}, {1, 3}});
        Vector rightHandSide = new ArrayVector(new double[]{4, 7});

        Vector solution = solver.solve(matrix, rightHandSide);

        assertEquals(1.0, solution.get(0), 1e-12);
        assertEquals(2.0, solution.get(1), 1e-12);
        assertEquals(-2.0, solver.determinant(matrix), 1e-12);
        Matrix inverse = solver.inverse(matrix);
        assertEquals(-1.5, inverse.get(0, 0), 1e-12);
        assertEquals(1.0, inverse.get(0, 1), 1e-12);
    }

    @Test
    void rejectsSingularAndMismatchedSystems() {
        Matrix singular = new RectangleArrayMatrix(new double[][]{{1, 2}, {2, 4}});
        Vector rightHandSide = new ArrayVector(new double[]{3, 6});

        assertEquals(0.0, solver.determinant(singular));
        assertThrows(IllegalArgumentException.class, () -> solver.solve(singular, rightHandSide));
        assertThrows(IllegalArgumentException.class, () -> solver.inverse(singular));
        assertThrows(IllegalArgumentException.class,
                () -> solver.solve(singular, new ArrayVector(new double[]{3})));
    }

    private static Matrix variantSevenMatrix() {
        return new RectangleArrayMatrix(new double[][]{
                {22.5575, 39.3926, 3.5581, 28.0089},
                {-30.2615, 29.7423, 34.8813, 12.8698},
                {11.6921, -24.9337, 28.8091, 21.3882},
                {-41.8493, 35.5320, -19.2518, -2.7015}
        });
    }
}
