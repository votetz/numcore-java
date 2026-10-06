package com.numcore.numeric.linearalgebra.vector;

import com.numcore.numeric.linearalgebra.vector.exception.VectorInvalidIndexException;

public final class VectorIndexValidator {

    private VectorIndexValidator() {}

    public static void check(int index, int size) {
        if (index < 0 || index >= size) { throw new VectorInvalidIndexException( new IndexOutOfBoundsException (
                "Vector index " + index + " is outside the range 0.." + (size - 1)), index );
        }
    }
}