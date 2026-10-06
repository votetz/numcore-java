package com.numcore.numeric.linearalgebra.matrix;

import com.numcore.numeric.linearalgebra.matrix.exception.MatrixInvalidIndexException;
import com.numcore.numeric.linearalgebra.matrix.impl.RectangleArrayMatrix;
import com.numcore.numeric.linearalgebra.vector.Vector;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RectangleArrayMatrixTest {

    private static Stream<Arguments> provideCellValues() {
        return Stream.of(
                Arguments.of(0, 0, 22.5575),
                Arguments.of(0, 4, -30.8114),
                Arguments.of(1, 2, 34.8813),
                Arguments.of(2, 3, 21.3882),
                Arguments.of(3, 1, 35.532)
        );
    }

    private static Stream<Arguments> provideInvalidIndexes() {
        return Stream.of(
                Arguments.of(-1, 0),
                Arguments.of(4, 0),
                Arguments.of(0, -1),
                Arguments.of(0, 5)
        );
    }

    @ParameterizedTest
    @MethodSource("provideCellValues")
    void shouldGetCellValue(int row, int col, double expected) {
        assertEquals(expected, matrix().get(row, col));
    }

    @ParameterizedTest
    @MethodSource("provideCellValues")
    void shouldSetCellValue(int row, int col, double value) {
        var matrix = matrix();

        matrix.set(row, col, value + 10.0);

        assertEquals(value + 10.0, matrix.get(row, col));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidIndexes")
    void shouldRejectInvalidCellIndex(int row, int col) {
        var exception = assertThrows(
                MatrixInvalidIndexException.class,
                () -> matrix().get(row, col)
        );

        assertEquals(row, exception.getRow());
        assertEquals(col, exception.getCol());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidIndexes")
    void shouldRejectInvalidSetIndex(int row, int col) {
        var exception = assertThrows(
                MatrixInvalidIndexException.class,
                () -> matrix().set(row, col, 10.0)
        );

        assertEquals(row, exception.getRow());
        assertEquals(col, exception.getCol());
    }

    @ParameterizedTest
    @MethodSource("provideMatrixData")
    void shouldReportDimensions(double[][] data) {
        var matrix = new RectangleArrayMatrix(copyOf(data));

        assertEquals(data.length, matrix.rows());
        assertEquals(data[0].length, matrix.cols());
    }

    @ParameterizedTest
    @MethodSource("provideMatrixData")
    void shouldSwapRows(double[][] data) {
        var matrix = new RectangleArrayMatrix(copyOf(data));
        int lastRow = data.length - 1;

        matrix.changeRows(0, lastRow);

        double[][] expected = copyOf(data);

        var temp = expected[0];
        expected[0] = expected[lastRow];
        expected[lastRow] = temp;

        assertMatrixEquals(expected, matrix);
    }

    @ParameterizedTest
    @MethodSource("provideMatrixData")
    void shouldSwapColumns(double[][] data) {
        var matrix = new RectangleArrayMatrix(copyOf(data));
        int lastColumn = data[0].length - 1;

        matrix.changeCols(0, lastColumn);

        double[][] expected = copyOf(data);

        for (int row = 0; row < data.length; row++) {
            var temp = expected[row][0];
            expected[row][0] = expected[row][lastColumn];
            expected[row][lastColumn] = temp;
        }

        assertMatrixEquals(expected, matrix);
    }

    @ParameterizedTest
    @MethodSource("provideMatrixData")
    void shouldTransposeMatrix(double[][] data) {
        var matrix = new RectangleArrayMatrix(copyOf(data));

        matrix.transpose();

        assertEquals(data[0].length, matrix.rows());
        assertEquals(data.length, matrix.cols());

        for (int row = 0; row < data.length; row++) {
            for (int col = 0; col < data[0].length; col++) {
                assertEquals(
                        data[row][col],
                        matrix.get(col, row)
                );
            }
        }
    }

    @ParameterizedTest
    @MethodSource("provideMatrixData")
    void shouldReturnRequestedRowAndColumn(double[][] data) {
        var matrix = new RectangleArrayMatrix(copyOf(data));

        Vector row = matrix.getRow(1);
        Vector col = matrix.getCol(1);

        for (int index = 0; index < data[0].length; index++) {
            assertEquals(data[1][index], row.get(index));
        }

        for (int index = 0; index < data.length; index++) {
            assertEquals(data[index][1], col.get(index));
        }
    }

    @ParameterizedTest
    @MethodSource("provideMatrixData")
    void shouldCreateIndependentCopy(double[][] data) {
        Matrix original = new RectangleArrayMatrix(copyOf(data));
        Matrix copy = original.copy();

        copy.set(0, 0, copy.get(0, 0) + 1.0);

        assertEquals(data[0][0], original.get(0, 0));
        assertEquals(data[0][0] + 1.0, copy.get(0, 0));

        assertEquals(data.length, copy.rows());
        assertEquals(data[0].length, copy.cols());
    }

    private static Stream<double[][]> provideMatrixData() {
        return Stream.<double[][]>of(
                new double[][]{
                        {22.5575, 39.3926, 3.5581, 28.0089, -30.8114},
                        {-30.2615, 29.7423, 34.8813, 12.8698, 49.4996},
                        {11.6921, -24.9337, 28.8091, 21.3882, 14.7635},
                        {-41.8493, 35.532, -19.2518, -2.7015, -32.5864}
                }
        );
    }

    private static RectangleArrayMatrix matrix() {
        return new RectangleArrayMatrix(
                new double[][]{
                        {22.5575, 39.3926, 3.5581, 28.0089, -30.8114},
                        {-30.2615, 29.7423, 34.8813, 12.8698, 49.4996},
                        {11.6921, -24.9337, 28.8091, 21.3882, 14.7635},
                        {-41.8493, 35.532, -19.2518, -2.7015, -32.5864}
                }
        );
    }

    private static double[][] copyOf(double[][] data) {
        double[][] copy = new double[data.length][];

        for (int row = 0; row < data.length; row++) {
            copy[row] = data[row].clone();
        }

        return copy;
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
                        actual.get(row, col)
                );
            }
        }
    }
}