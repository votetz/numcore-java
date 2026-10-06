package com.numcore.numeric.linearalgebra.vector.impl;

import com.numcore.numeric.linearalgebra.vector.Vector;
import com.numcore.numeric.linearalgebra.vector.VectorIndexValidator;

import java.util.List;

public class ListVector implements Vector {

    private final List<Double> values;

    public ListVector(List<Double> values) {
        this.values = values;
    }

    @Override
    public double get(int index) {
        VectorIndexValidator.check(index, values.size());
        return values.get(index);
    }

    @Override
    public void set(int index, double value) {
        VectorIndexValidator.check(index, values.size());
        values.set(index, value);
    }

    @Override
    public void change(int index, double value) {
        VectorIndexValidator.check(index, values.size());
        values.set(index, value);
    }

    @Override
    public int size() {
        return values.size();
    }
}