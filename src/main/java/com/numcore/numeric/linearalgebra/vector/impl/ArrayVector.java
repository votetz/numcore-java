package com.numcore.numeric.linearalgebra.vector.impl;

import com.numcore.numeric.linearalgebra.vector.Vector;
import com.numcore.numeric.linearalgebra.vector.VectorIndexValidator;

public class ArrayVector implements Vector {
    private final double[] data;

    public ArrayVector(int size) {
        this.data = new double[size];
    }

    public ArrayVector(double[] data) {
        this.data = data.clone();
    }

    @Override
    public int size() {
        return data.length;
    }

    @Override
    public double get(int index) {
        return data[index];
    }

    @Override
    public void change(int index, double value) {
        data[index] += value;
    }

    @Override
    public void set(int index, double value) {
        data[index] = value;
    }
}

