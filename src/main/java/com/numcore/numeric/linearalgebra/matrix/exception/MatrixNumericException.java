package com.numcore.numeric.linearalgebra.matrix.exception;

import com.numcore.numeric.linearalgebra.exception.NumericException;

public class MatrixNumericException extends NumericException {
    public MatrixNumericException() {
        super();
    }

    public MatrixNumericException(String message) {
        super(message);
    }

    public MatrixNumericException(String message, Throwable cause) {
        super(message, cause);
    }

    public MatrixNumericException(Throwable cause) {
        super(cause);
    }
}