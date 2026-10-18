package com.numcore.numeric.linearalgebra.iterative;

import com.numcore.numeric.linearalgebra.matrix.Matrix;
import com.numcore.numeric.linearalgebra.vector.Vector;

public record LinearSystem(Matrix coefficients, Vector rightHandSide) {
}
