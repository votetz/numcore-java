package com.numcore.numeric.linearalgebra.tridiagonal;

import com.numcore.numeric.linearalgebra.vector.Vector;

public interface TridiagonalSolver {
    Vector solve(TridiagonalSystem system);
}
