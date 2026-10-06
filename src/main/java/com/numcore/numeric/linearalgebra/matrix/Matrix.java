package com.numcore.numeric.linearalgebra.matrix;

import com.numcore.numeric.linearalgebra.vector.Vector;
import com.numcore.numeric.linearalgebra.Copyable;

public interface Matrix extends Copyable<Matrix> {
    int rows();
    int cols();
    double get(int row, int col);
    void set(int row, int col, double value);
    void changeRows(int row1, int row2);
    void changeCols(int col1, int col2);
    void transpose();

    Vector getRow(int row);
    Vector getCol(int col);
}
