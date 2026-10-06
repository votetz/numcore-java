package com.numcore.numeric.linearalgebra.vector;

public interface Vector {
    int size();
    double get(int index);
    void change(int index, double value);
    void set(int index, double value);
}
