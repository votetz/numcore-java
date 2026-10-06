package com.numcore.numeric.linearalgebra.matrix;

import com.numcore.numeric.linearalgebra.matrix.exception.MatrixInvalidISizeException;
import com.numcore.numeric.linearalgebra.matrix.impl.RectangleArrayMatrix;
import com.numcore.numeric.linearalgebra.matrix.impl.RectangleArrayMatrixOperation;
import com.numcore.numeric.linearalgebra.vector.Vector;
import com.numcore.numeric.linearalgebra.vector.impl.ArrayVector;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RectangleArrayMatrixOperationTest {

    private final RectangleArrayMatrixOperation operation =
            new RectangleArrayMatrixOperation();

    private static Stream<Arguments> provideBinaryMatrixOperations() {
        return Stream.of(
                Arguments.of(
                        new double[][]{
                                {22.5575, 39.3926, 3.5581, 28.0089, -30.8114},
                                {-30.2615, 29.7423, 34.8813, 12.8698, 49.4996},
                                {11.6921, -24.9337, 28.8091, 21.3882, 14.7635},
                                {-41.8493, 35.532, -19.2518, -2.7015, -32.5864}
                        },
                        new double[][]{
                                {22.5575, 39.3926, 3.5581, 28.0089, -30.8114},
                                {-30.2615, 29.7423, 34.8813, 12.8698, 49.4996},
                                {11.6921, -24.9337, 28.8091, 21.3882, 14.7635},
                                {-41.8493, 35.532, -19.2518, -2.7015, -32.5864}
                        },
                        new double[][]{
                                {45.1150, 78.7852, 7.1162, 56.0178, -61.6228},
                                {-60.5230, 59.4846, 69.7626, 25.7396, 98.9992},
                                {23.3842, -49.8674, 57.6182, 42.7764, 29.5270},
                                {-83.6986, 71.0640, -38.5036, -5.4030, -65.1728}
                        },
                        new double[][]{
                                {0.0, 0.0, 0.0, 0.0, 0.0},
                                {0.0, 0.0, 0.0, 0.0, 0.0},
                                {0.0, 0.0, 0.0, 0.0, 0.0},
                                {0.0, 0.0, 0.0, 0.0, 0.0}
                        }
                )
        );
    }

    private static Stream<Arguments> provideMatrixMultiplication() {
        return Stream.of(
                Arguments.of(
                        new double[][]{
                                {22.5575, 39.3926, 3.5581, 28.0089, -30.8114},
                                {-30.2615, 29.7423, 34.8813, 12.8698, 49.4996},
                                {11.6921, -24.9337, 28.8091, 21.3882, 14.7635},
                                {-41.8493, 35.532, -19.2518, -2.7015, -32.5864}
                        },
                        new double[][]{
                                {22.5575, -30.2615, 11.6921, -41.8493},
                                {39.3926, 29.7423, -24.9337, 35.5320},
                                {3.5581, 34.8813, 28.8091, -19.2518},
                                {28.0089, 12.8698, 21.3882, -2.7015},
                                {-30.8114, 49.4996, 14.7635, -32.5864}
                        },
                        new double[][]{
                                {3807.1186658, -551.56913996, -471.77721508, 1315.5490105},
                                {-551.56913996, 5632.9100334, 915.54199113, 3.91665407},
                                {-471.77721508, 915.54199113, 2263.7748724, -2468.746999},
                                {1315.5490105, 3.91665407, -2468.746999, 4453.6903049}
                        }
                )
        );
    }

    private static Stream<Arguments> provideScalarMultiplication() {
        return Stream.of(
                Arguments.of(
                        new double[][]{
                                {22.5575, 39.3926, 3.5581, 28.0089, -30.8114},
                                {-30.2615, 29.7423, 34.8813, 12.8698, 49.4996},
                                {11.6921, -24.9337, 28.8091, 21.3882, 14.7635},
                                {-41.8493, 35.532, -19.2518, -2.7015, -32.5864}
                        },
                        -0.5,
                        new double[][]{
                                {-11.27875, -19.6963, -1.77905, -14.00445, 15.4057},
                                {15.13075, -14.87115, -17.44065, -6.4349, -24.7498},
                                {-5.84605, 12.46685, -14.40455, -10.6941, -7.38175},
                                {20.92465, -17.766, 9.6259, 1.35075, 16.2932}
                        }
                )
        );
    }

    private static Stream<Arguments> provideVectorMultiplication() {
        return Stream.of(
                Arguments.of(
                        new double[][]{
                                {22.5575, 39.3926, 3.5581, 28.0089, -30.8114},
                                {-30.2615, 29.7423, 34.8813, 12.8698, 49.4996},
                                {11.6921, -24.9337, 28.8091, 21.3882, 14.7635},
                                {-41.8493, 35.532, -19.2518, -2.7015, -32.5864}
                        },
                        new double[]{1.0, 2.0, 3.0, 4.0, 5.0},
                        new double[]{69.9956, 432.8442, 207.6223, -202.2787}
                )
        );
    }

    @ParameterizedTest
    @MethodSource("provideBinaryMatrixOperations")
    void shouldAddMatrices(
            double[][] first,
            double[][] second,
            double[][] expected,
            double[][] ignored) {

        assertMatrixEquals(
                expected,
                operation.add(matrix(first), matrix(second))
        );
    }

    @ParameterizedTest
    @MethodSource("provideBinaryMatrixOperations")
    void shouldSubtractMatrices(
            double[][] first,
            double[][] second,
            double[][] ignored,
            double[][] expected) {

        assertMatrixEquals(
                expected,
                operation.subtract(matrix(first), matrix(second))
        );
    }

    @ParameterizedTest
    @MethodSource("provideMatrixMultiplication")
    void shouldMultiplyMatrices(
            double[][] first,
            double[][] second,
            double[][] expected) {

        assertMatrixEquals(
                expected,
                operation.multiply(matrix(first), matrix(second))
        );
    }

    @ParameterizedTest
    @MethodSource("provideScalarMultiplication")
    void shouldMultiplyMatrixByScalar(
            double[][] values,
            double scalar,
            double[][] expected) {

        assertMatrixEquals(
                expected,
                operation.multiplyScalar(matrix(values), scalar)
        );
    }

    @ParameterizedTest
    @MethodSource("provideVectorMultiplication")
    void shouldMultiplyMatrixByVector(
            double[][] values,
            double[] vectorValues,
            double[] expected) {

        Vector actual = operation.multiply(
                matrix(values),
                new ArrayVector(vectorValues)
        );

        assertArrayEquals(expected, valuesOf(actual), 0.0001);
    }

    @ParameterizedTest
    @MethodSource("provideInvalidMatrixMultiplicationSizes")
    void shouldRejectInvalidMatrixMultiplicationSizes(
            double[][] first,
            double[][] second) {

        var exception = assertThrows(
                MatrixInvalidISizeException.class,
                () -> operation.multiply(
                        matrix(first),
                        matrix(second)
                )
        );

        assertEquals(first[0].length, exception.getRow());
        assertEquals(second.length, exception.getCol());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidVectorMultiplicationSizes")
    void shouldRejectInvalidVectorMultiplicationSizes(
            double[][] values,
            double[] vectorValues) {

        var exception = assertThrows(
                MatrixInvalidISizeException.class,
                () -> operation.multiply(
                        matrix(values),
                        new ArrayVector(vectorValues)
                )
        );

        assertEquals(values[0].length, exception.getRow());
        assertEquals(vectorValues.length, exception.getCol());
    }

    private static Stream<Arguments> provideInvalidMatrixMultiplicationSizes() {
        return Stream.of(
                Arguments.of(
                        new double[][]{
                                {22.5575, 39.3926, 3.5581, 28.0089, -30.8114}
                        },
                        new double[][]{
                                {1.0},
                                {2.0},
                                {3.0},
                                {4.0}
                        }
                ),
                Arguments.of(
                        new double[][]{
                                {22.5575},
                                {-30.2615},
                                {11.6921},
                                {-41.8493}
                        },
                        new double[][]{
                                {1.0, 2.0},
                                {3.0, 4.0}
                        }
                )
        );
    }

    private static Stream<Arguments> provideInvalidVectorMultiplicationSizes() {
        return Stream.of(
                Arguments.of(
                        new double[][]{
                                {22.5575, 39.3926, 3.5581, 28.0089, -30.8114}
                        },
                        new double[]{1.0, 2.0, 3.0, 4.0}
                ),
                Arguments.of(
                        new double[][]{
                                {22.5575},
                                {-30.2615},
                                {11.6921},
                                {-41.8493}
                        },
                        new double[]{1.0, 2.0}
                )
        );
    }

    private static Matrix matrix(double[][] values) {
        return new RectangleArrayMatrix(copyOf(values));
    }

    private static double[][] copyOf(double[][] values) {
        double[][] copy = new double[values.length][];

        for (int row = 0; row < values.length; row++) {
            copy[row] = values[row].clone();
        }

        return copy;
    }

    private static double[] valuesOf(Vector vector) {
        double[] values = new double[vector.size()];

        for (int index = 0; index < vector.size(); index++) {
            values[index] = vector.get(index);
        }

        return values;
    }

    private static void assertMatrixEquals(
            double[][] expected,
            Matrix actual) {

        assertEquals(expected.length, actual.rows());
        assertEquals(expected[0].length, actual.cols());

        for (int row = 0; row < expected.length; row++) {
            for (int col = 0; col < expected[row].length; col++) {
                assertEquals(
                        expected[row][col],
                        actual.get(row, col),
                        0.0001
                );
            }
        }
    }
}