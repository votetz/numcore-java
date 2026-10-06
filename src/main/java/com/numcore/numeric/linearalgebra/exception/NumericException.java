package com.numcore.numeric.linearalgebra.exception;

public class NumericException extends RuntimeException {
    public NumericException() {
        super();
    }

    public NumericException(String message) {
        super(message);
    }

    public NumericException(String message, Throwable cause) {
        super(message, cause);
    }

    public NumericException(Throwable cause) {
        super(cause);
    }
}