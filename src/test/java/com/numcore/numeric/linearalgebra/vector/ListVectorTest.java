package com.numcore.numeric.linearalgebra.vector;

import com.numcore.numeric.linearalgebra.vector.impl.ListVector;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ListVectorTest {

    @ParameterizedTest
    @MethodSource("provideValues")
    void shouldGetValue(int index, double expected) {
        var vector = vector();

        assertEquals(expected, vector.get(index));
    }

    @ParameterizedTest
    @MethodSource("provideValues")
    void shouldSetValue(int index, double value) {
        var vector = vector();

        vector.set(index, value + 10.0);

        assertEquals(value + 10.0, vector.get(index));
    }

    @ParameterizedTest
    @MethodSource("provideValues")
    void shouldChangeValue(int index, double value) {
        var vector = vector();

        vector.change(index, value + 10.0);

        assertEquals(value + 10.0, vector.get(index));
    }

    @ParameterizedTest
    @MethodSource("provideVectors")
    void shouldReportSize(List<Double> data) {
        var vector = new ListVector(new ArrayList<>(data));

        assertEquals(data.size(), vector.size());
    }

    private static Stream<Arguments> provideValues() {
        return Stream.of(
                Arguments.of(0, 22.5575),
                Arguments.of(1, 39.3926),
                Arguments.of(2, 3.5581),
                Arguments.of(3, 28.0089),
                Arguments.of(4, -30.8114)
        );
    }

    private static Stream<Arguments> provideVectors() {
        return Stream.of(
                Arguments.of(List.of(
                        22.5575,
                        39.3926,
                        3.5581,
                        28.0089,
                        -30.8114
                )),
                Arguments.of(List.of(
                        -30.2615,
                        29.7423,
                        34.8813
                ))
        );
    }

    private static ListVector vector() {
        return new ListVector(new ArrayList<>(List.of(
                22.5575,
                39.3926,
                3.5581,
                28.0089,
                -30.8114
        )));
    }
}