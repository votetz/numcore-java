package com.numcore.numeric.linearalgebra.matrix;

import com.numcore.numeric.linearalgebra.vector.Vector;

public interface MatrixOperation {
    Matrix add(Matrix m1, Matrix m2);
    Matrix subtract(Matrix m1, Matrix m2);
    Matrix multiply(Matrix m1, Matrix m2);
    Matrix multiplyScalar(Matrix m, double scalar);
    Vector multiply(Matrix m, Vector v);
}
