package com.numcore.numeric.linearalgebra.matrix;

import com.numcore.numeric.linearalgebra.matrix.exception.MatrixInvalidIndexException;

public final class MatrixIndexValidator {

    private MatrixIndexValidator() {
    }

    public static void check(int row, int column, int rows, int columns) {
        if (row < 0 || row >= rows) {
            throw new MatrixInvalidIndexException(new IndexOutOfBoundsException(
                            "Row index " + row + " is out of bounds"), row, column);
        }

        if (column < 0 || column >= columns) {
            throw new MatrixInvalidIndexException(new IndexOutOfBoundsException(
                            "Column index " + column + " is out of bounds"), row, column);
        }
    }
}