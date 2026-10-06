package com.numcore.numeric.linearalgebra.vector.impl;

import com.numcore.numeric.linearalgebra.vector.Vector;
import com.numcore.numeric.linearalgebra.vector.VectorOperation;

import java.util.ArrayList;
import java.util.List;

public class ListVectorOperation implements VectorOperation {

    @Override
    public Vector add(Vector first, Vector second) {
        int size = Math.min(first.size(), second.size());
        List<Double> result = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            result.add(first.get(i) + second.get(i));
        }

        return new ListVector(result);
    }

    @Override
    public Vector subtract(Vector first, Vector second) {
        int size = Math.min(first.size(), second.size());
        List<Double> result = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            result.add(first.get(i) - second.get(i));
        }

        return new ListVector(result);
    }

    @Override
    public Vector multiply(Vector vector, double scalar) {
        List<Double> result = new ArrayList<>(vector.size());

        for (int i = 0; i < vector.size(); i++) {
            result.add(vector.get(i) * scalar);
        }

        return new ListVector(result);
    }

    @Override
    public Vector copy() {
        return new ListVector(new ArrayList<>());
    }
}