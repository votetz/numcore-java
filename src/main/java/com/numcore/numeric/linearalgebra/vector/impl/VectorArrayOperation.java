package com.numcore.numeric.linearalgebra.vector.impl;

import com.numcore.numeric.linearalgebra.vector.Vector;
import com.numcore.numeric.linearalgebra.vector.VectorOperation;

public class VectorArrayOperation implements VectorOperation {

    @Override
    public Vector add(Vector first, Vector second) {
        int length = Math.min(first.size(), second.size());
        double[] values = new double[length];

        for (int index = 0; index < length; index++) {
            values[index] = first.get(index) + second.get(index);
        }

        return new ArrayVector(values);
    }

    @Override
    public Vector subtract(Vector first, Vector second) {
        int length = Math.min(first.size(), second.size());
        double[] values = new double[length];

        for (int index = 0; index < length; index++) {
            values[index] = first.get(index) - second.get(index);
        }

        return new ArrayVector(values);
    }

    @Override
    public Vector multiply(Vector vector, double scalar) {
        double[] values = new double[vector.size()];

        for (int index = 0; index < vector.size(); index++) {
            values[index] = vector.get(index) * scalar;
        }

        return new ArrayVector(values);
    }

    @Override
    public Vector copy() {
        return new ArrayVector(new double[0]);
    }
}