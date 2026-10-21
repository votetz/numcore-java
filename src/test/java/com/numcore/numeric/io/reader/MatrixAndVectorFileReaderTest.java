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

    @org.junit.jupiter.api.Test
    void readsVariantSevenMatrixAndRightHandSideFromResources() throws IOException {
        Path matrixFile = Path.of("src/test/resources/matrix.txt");
        Path vectorFile = Path.of("src/test/resources/vector.txt");

        Matrix matrix = new RectangleArrayMatrixFileReader().read(matrixFile);
        Vector vector = new ArrayVectorFileReader().read(vectorFile);

        double[][] expectedMatrix = {
                {22.5575, 39.3926, 3.5581, 28.0089},
                {-30.2615, 29.7423, 34.8813, 12.8698},
                {11.6921, -24.9337, 28.8091, 21.3882},
                {-41.8493, 35.5320, -19.2518, -2.7015}
        };
        double[] expectedVector = {-30.8114, 49.4996, 14.7635, -32.5864};

        assertEquals(4, matrix.rows());
        assertEquals(4, matrix.cols());
        assertEquals(expectedVector.length, vector.size());
        for (int row = 0; row < expectedMatrix.length; row++) {
            for (int column = 0; column < expectedMatrix[row].length; column++) {
                assertEquals(expectedMatrix[row][column], matrix.get(row, column));
            }
            assertEquals(expectedVector[row], vector.get(row));
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
                Arguments.of("22,5575 39,3926 3,5581 28,0089\n-30,2615 29,7423 34,8813 12,8698",
                        new double[][]{{22.5575, 39.3926, 3.5581, 28.0089},
                                {-30.2615, 29.7423, 34.8813, 12.8698}}),
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
                Arguments.of("-30,8114\n49,4996\n14,7635\n-32,5864\n",
                        new double[]{-30.8114, 49.4996, 14.7635, -32.5864}),
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
