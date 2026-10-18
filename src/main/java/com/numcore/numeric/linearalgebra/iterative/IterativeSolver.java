package com.numcore.numeric.linearalgebra.iterative;

import com.numcore.numeric.linearalgebra.matrix.Matrix;
import com.numcore.numeric.linearalgebra.vector.Vector;

public interface IterativeSolver {
    IterationResult solve(Matrix coefficients, Vector rightHandSide, double epsilon, int maxIterations);
}
