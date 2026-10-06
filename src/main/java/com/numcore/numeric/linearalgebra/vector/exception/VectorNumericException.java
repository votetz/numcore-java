package com.numcore.numeric.linearalgebra.vector.exception;

import com.numcore.numeric.linearalgebra.exception.NumericException;

public class VectorNumericException extends NumericException {
    public VectorNumericException() {
        super();
    }

    public VectorNumericException(String message) {
        super(message);
    }

    public VectorNumericException(String message, Throwable cause) {
        super(message, cause);
    }

    public VectorNumericException(Throwable cause) {
        super(cause);
    }
}