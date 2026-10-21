package com.numcore.numeric.linearalgebra.tridiagonal;

import com.numcore.numeric.linearalgebra.tridiagonal.impl.ThomasAlgorithmSolver;
import com.numcore.numeric.linearalgebra.vector.Vector;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ThomasAlgorithmSolverTest {
    @ParameterizedTest
    @MethodSource("systems")
    void solvesTridiagonalSystem(TridiagonalSystem system, double[] expectedSolution) {
        Vector solution = new ThomasAlgorithmSolver().solve(system);

        assertEquals(expectedSolution.length, solution.size());
        for (int index = 0; index < expectedSolution.length; index++) {
            assertEquals(expectedSolution[index], solution.get(index), 1e-8);
        }

        double[] lower = system.lowerDiagonal();
        double[] main = system.mainDiagonal();
        double[] upper = system.upperDiagonal();
        double[] rightHandSide = system.rightHandSide();
        for (int row = 0; row < solution.size(); row++) {
            double leftHandSide = main[row] * solution.get(row);
            if (row > 0) {
                leftHandSide += lower[row] * solution.get(row - 1);
            }
            if (row < solution.size() - 1) {
                leftHandSide += upper[row] * solution.get(row + 1);
            }
            assertEquals(rightHandSide[row], leftHandSide, 1e-9);
        }
    }

    @Test
    void rejectsValuesOutsideTheThreeDiagonals() {
        assertThrows(IllegalArgumentException.class, () -> new TridiagonalSystem(
                new double[]{1, 2}, new double[]{3, 4},
                new double[]{5, 0}, new double[]{6, 7}));
        assertThrows(IllegalArgumentException.class, () -> new TridiagonalSystem(
                new double[]{0, 2}, new double[]{3, 4},
                new double[]{5, 1}, new double[]{6, 7}));
    }

    private static Stream<Arguments> systems() {
        return Stream.of(
                Arguments.of(
                        new TridiagonalSystem(
                                new double[]{0, 1, 1},
                                new double[]{2, 2, 2},
                                new double[]{1, 1, 0},
                                new double[]{4, 8, 8}),
                        new double[]{1, 2, 3}),
                Arguments.of(
                        variantSeven(),
                        new double[]{1.7198451577, -1.1617889060, -0.2236727973,
                                0.0385622229, 2.2155435139})
        );
    }

    private static TridiagonalSystem variantSeven() {
        return new TridiagonalSystem(
                new double[]{0, 22.766, -19.418, -34.511, 4.509},
                new double[]{7.605, 16.778, -39.142, -42.065, -9.102},
                new double[]{41.155, -18.498, 35.122, 14.099, 0},
                new double[]{-34.734, 23.799, 32.669, 37.334, -19.992});
    }
}
