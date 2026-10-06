package com.numcore.numeric.linearalgebra.vector;

import com.numcore.numeric.linearalgebra.vector.impl.ArrayVector;
import com.numcore.numeric.linearalgebra.vector.impl.VectorArrayOperation;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ArrayVectorOperationTest {

    private final VectorArrayOperation operation = new VectorArrayOperation();

    @ParameterizedTest
    @MethodSource("provideBinaryOperations")
    void shouldAddVectors(
            double[] first,
            double[] second,
            double[] expected) {

        Vector actual = operation.add(
                new ArrayVector(first),
                new ArrayVector(second)
        );

        assertVectorEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("provideBinaryOperations")
    void shouldSubtractVectors(
            double[] first,
            double[] second,
            double[] ignored) {

        Vector actual = operation.subtract(
                new ArrayVector(first),
                new ArrayVector(second)
        );

        double[] expected = new double[first.length];

        for (int i = 0; i < first.length; i++) {
            expected[i] = first[i] - second[i];
        }

        assertVectorEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("provideScalarOperations")
    void shouldMultiplyVectorByScalar(
            double[] values,
            double scalar,
            double[] expected) {

        Vector actual = operation.multiply(
                new ArrayVector(values),
                scalar
        );

        assertVectorEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("provideDotProduct")
    void shouldMultiplyVectors(
            double[] first,
            double[] second,
            double expected) {

        double actual = operation.multiply(
                new ArrayVector(first),
                new ArrayVector(second)
        );

        assertEquals(expected, actual, 0.0000001);
    }

    private static Stream<Arguments> provideBinaryOperations() {
        return Stream.of(
                Arguments.of(
                        new double[]{1.0, 2.0, 3.0},
                        new double[]{4.0, 5.0, 6.0},
                        new double[]{5.0, 7.0, 9.0}
                ),
                Arguments.of(
                        new double[]{22.5575, 39.3926},
                        new double[]{-30.2615, 29.7423},
                        new double[]{-7.704, 68.1349}
                )
        );
    }

    private static Stream<Arguments> provideScalarOperations() {
        return Stream.of(
                Arguments.of(
                        new double[]{1.0, -2.0, 3.0},
                        2.0,
                        new double[]{2.0, -4.0, 6.0}
                ),
                Arguments.of(
                        new double[]{22.5575, 39.3926},
                        -0.5,
                        new double[]{-11.27875, -19.6963}
                )
        );
    }

    private static Stream<Arguments> provideDotProduct() {
        return Stream.of(
                Arguments.of(
                        new double[]{1.0, 2.0, 3.0},
                        new double[]{4.0, 5.0, 6.0},
                        32.0
                ),
                Arguments.of(
                        new double[]{22.5575, 39.3926},
                        new double[]{-30.2615, 29.7423},
                        498.17771949
                )
        );
    }

    private static void assertVectorEquals(
            double[] expected,
            Vector actual) {

        assertEquals(expected.length, actual.size());

        for (int index = 0; index < expected.length; index++) {
            assertEquals(
                    expected[index],
                    actual.get(index),
                    0.0000001
            );
        }
    }
}