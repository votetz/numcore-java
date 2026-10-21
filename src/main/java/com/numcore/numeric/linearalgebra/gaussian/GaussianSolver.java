package com.numcore.numeric.linearalgebra.gaussian;

import com.numcore.numeric.linearalgebra.matrix.Matrix;
import com.numcore.numeric.linearalgebra.vector.Vector;

public interface GaussianSolver {
    Vector solve(Matrix coefficients, Vector rightHandSide);

    Vector residual(Matrix coefficients, Vector solution, Vector rightHandSide);

    double determinant(Matrix coefficients);

    Matrix inverse(Matrix coefficients);
}
