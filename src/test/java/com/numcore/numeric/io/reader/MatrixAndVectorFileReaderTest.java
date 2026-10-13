package com.numcore.numeric.io.reader;

import com.numcore.numeric.io.reader.impl.ArrayVectorFileReader;
import com.numcore.numeric.io.reader.impl.RectangleArrayMatrixFileReader;
import com.numcore.numeric.linearalgebra.matrix.Matrix;
import com.numcore.numeric.linearalgebra.vector.Vector;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MatrixAndVectorFileReaderTest {
    @TempDir
    Path tempDir;

    @ParameterizedTest
    @MethodSource("matrixFiles")
    void readsMatrix(String content, double[][] expected) throws IOException {
        Path file = writeFile("matrix.txt", content);

        Matrix matrix = new RectangleArrayMatrixFileReader().read(file);

        assertEquals(expected.length, matrix.rows());
        assertEquals(expected[0].length, matrix.cols());
        for (int row = 0; row < expected.length; row++) {
            for (int column = 0; column < expected[row].length; column++) {
                assertEquals(expected[row][column], matrix.get(row, column));
            }
        }
    }

    @ParameterizedTest
    @MethodSource("invalidMatrixFiles")
    void rejectsInvalidMatrix(String content, Class<? extends Throwable> exceptionType) throws IOException {
        Path file = writeFile("invalid-matrix.txt", content);

        assertThrows(
                exceptionType,
                () -> new RectangleArrayMatrixFileReader().read(file));
    }

    @ParameterizedTest
    @MethodSource("vectorFiles")
    void readsVector(String content, double[] expected) throws IOException {
        Path file = writeFile("vector.txt", content);

        Vector vector = new ArrayVectorFileReader().read(file);

        assertEquals(expected.length, vector.size());
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], vector.get(i));
        }
    }

    @ParameterizedTest
    @MethodSource("invalidVectorFiles")
    void rejectsInvalidVector(String content, Class<? extends Throwable> exceptionType) throws IOException {
        Path file = writeFile("invalid-vector.txt", content);

        assertThrows(
                exceptionType,
                () -> new ArrayVectorFileReader().read(file));
    }

    private Path writeFile(String name, String content) throws IOException {
        Path file = tempDir.resolve(name);
        return Files.writeString(file, content);
    }

    private static Stream<Arguments> matrixFiles() {
        return Stream.of(
                Arguments.of("1.0 2.0 3.0\n4.0 5.0 6.0\n", new double[][]{{1, 2, 3}, {4, 5, 6}}),
                Arguments.of("  -1\t2.5  \n\n  3e2 4  \n", new double[][]{{-1, 2.5}, {300, 4}})
        );
    }

    private static Stream<Arguments> invalidMatrixFiles() {
        return Stream.of(
                Arguments.of("", IllegalArgumentException.class),
                Arguments.of("1 2\n3\n", IllegalArgumentException.class),
                Arguments.of("1 nope\n", NumberFormatException.class)
        );
    }

    private static Stream<Arguments> vectorFiles() {
        return Stream.of(
                Arguments.of("1.0\n2.0\n3.0\n", new double[]{1, 2, 3}),
                Arguments.of("  -1.5  2\t3e1\n\n4\n", new double[]{-1.5, 2, 30, 4})
        );
    }

    private static Stream<Arguments> invalidVectorFiles() {
        return Stream.of(
                Arguments.of("\n \n", IllegalArgumentException.class),
                Arguments.of("1\nabc\n", NumberFormatException.class)
        );
    }
}
