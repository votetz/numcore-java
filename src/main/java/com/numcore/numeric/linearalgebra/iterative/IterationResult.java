package com.numcore.numeric.linearalgebra.iterative;

import com.numcore.numeric.linearalgebra.vector.Vector;

public record IterationResult(Vector solution, int iterations, boolean converged) {
}
