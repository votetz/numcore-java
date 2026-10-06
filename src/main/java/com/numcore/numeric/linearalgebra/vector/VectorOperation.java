package com.numcore.numeric.linearalgebra.vector;

import com.numcore.numeric.linearalgebra.Copyable;

public interface VectorOperation extends Copyable<Vector> {
    Vector add(Vector v1, Vector v2);
    Vector subtract(Vector v1, Vector v2);

    default double multiply(Vector v1, Vector v2) {
        double result = 0;
        for (int i = 0; i < v1.size(); i++) {
            result += v1.get(i) * v2.get(i);
        }
        return result;
    }

    Vector multiply(Vector other, double scalar);
}
