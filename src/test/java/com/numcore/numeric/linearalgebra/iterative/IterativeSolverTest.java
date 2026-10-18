package com.numcore.numeric.linearalgebra.iterative;

import com.numcore.numeric.linearalgebra.iterative.impl.GaussSeidelSolver;
import com.numcore.numeric.linearalgebra.iterative.impl.SimpleIterationSolver;
import com.numcore.numeric.linearalgebra.matrix.impl.RectangleArrayMatrix;
import com.numcore.numeric.linearalgebra.vector.impl.ArrayVector;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IterativeSolverTest {
    private static final double EPSILON = 1e-10;
    private static final int MAX_ITERATIONS = 1_000;

    @ParameterizedTest
    @MethodSource("solversAndSystems")
    void solvesLinearSystem(IterativeSolver solver, double[][] coefficients,
                            double[] rightHandSide, double[] expectedSolution) {
        IterationResult result = solver.solve(
                new RectangleArrayMatrix(coefficients),
                new ArrayVector(rightHandSide),
                EPSILON,
                MAX_ITERATIONS);

        assertTrue(result.converged());
        assertTrue(result.iterations() > 0);
        assertEquals(expectedSolution.length, result.solution().size());
        for (int i = 0; i < expectedSolution.length; i++) {
            assertEquals(expectedSolution[i], result.solution().get(i), 1e-8);
        }
    }

    @ParameterizedTest
    @MethodSource("solvers")
    void reportsWhenMaximumIterationsAreReached(IterativeSolver solver) {
        IterationResult result = solver.solve(
                new RectangleArrayMatrix(new double[][]{{10, -1, 2, 0}, {-1, 11, -1, 3},
                        {2, -1, 10, -1}, {0, 3, -1, 8}}),
                new ArrayVector(new double[]{6, 25, -11, 15}),
                EPSILON,
                1);

        assertFalse(result.converged());
        assertEquals(1, result.iterations());
    }

    private static Stream<Arguments> solversAndSystems() {
        return Stream.of(
                Arguments.of(new SimpleIterationSolver(),
                        new double[][]{{10, -1, 2, 0}, {-1, 11, -1, 3},
                                {2, -1, 10, -1}, {0, 3, -1, 8}},
                        new double[]{6, 25, -11, 15},
                        new double[]{1, 2, -1, 1}),
                Arguments.of(new GaussSeidelSolver(),
                        new double[][]{{10, -1, 2, 0}, {-1, 11, -1, 3},
                                {2, -1, 10, -1}, {0, 3, -1, 8}},
                        new double[]{6, 25, -11, 15},
                        new double[]{1, 2, -1, 1}),
                Arguments.of(new SimpleIterationSolver(),
                        new double[][]{{4, 1}, {2, 3}},
                        new double[]{6, 8},
                        new double[]{1, 2}),
                Arguments.of(new GaussSeidelSolver(),
                        new double[][]{{4, 1}, {2, 3}},
                        new double[]{6, 8},
                        new double[]{1, 2})
        );
    }

    private static Stream<Arguments> solvers() {
        return Stream.of(
                Arguments.of(new SimpleIterationSolver()),
                Arguments.of(new GaussSeidelSolver())
        );
    }

}
