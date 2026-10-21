package com.numcore.numeric.io.writer;

import com.numcore.numeric.io.reader.impl.ArrayVectorFileReader;
import com.numcore.numeric.io.reader.impl.RectangleArrayMatrixFileReader;
import com.numcore.numeric.io.writer.impl.ArrayVectorFileWriter;
import com.numcore.numeric.io.writer.impl.RectangleArrayMatrixFileWriter;
import com.numcore.numeric.linearalgebra.matrix.Matrix;
import com.numcore.numeric.linearalgebra.matrix.impl.RectangleArrayMatrix;
import com.numcore.numeric.linearalgebra.vector.Vector;
import com.numcore.numeric.linearalgebra.vector.impl.ArrayVector;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MatrixAndVectorFileWriterTest {
    @TempDir
    Path tempDir;

    @Test
    void readsAndWritesVariantSevenMatrixAndRightHandSide() throws IOException {
        Path matrixFile = Path.of("src/test/resources/matrix.txt");
        Path vectorFile = Path.of("src/test/resources/vector.txt");
        Matrix expectedMatrix = new RectangleArrayMatrixFileReader().read(matrixFile);
        Vector expectedVector = new ArrayVectorFileReader().read(vectorFile);

        Path writtenMatrixFile = tempDir.resolve("variant-seven-matrix.txt");
        Path writtenVectorFile = tempDir.resolve("variant-seven-vector.txt");
        new RectangleArrayMatrixFileWriter().write(expectedMatrix, writtenMatrixFile);
        new ArrayVectorFileWriter().write(expectedVector, writtenVectorFile);

        assertMatrixEquals(expectedMatrix,
                new RectangleArrayMatrixFileReader().read(writtenMatrixFile));
        Vector actualVector = new ArrayVectorFileReader().read(writtenVectorFile);
        assertEquals(expectedVector.size(), actualVector.size());
        for (int i = 0; i < expectedVector.size(); i++) {
            assertEquals(expectedVector.get(i), actualVector.get(i));
        }
    }

    @ParameterizedTest
    @MethodSource("matrices")
    void matrixWriterOutputCanBeReadBack(double[][] values) throws IOException {
        Path file = tempDir.resolve("matrix.txt");
        Matrix expected = new RectangleArrayMatrix(values);

        new RectangleArrayMatrixFileWriter().write(expected, file);
        Matrix actual = new RectangleArrayMatrixFileReader().read(file);

        assertMatrixEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("vectors")
    void vectorWriterOutputCanBeReadBack(double[] values) throws IOException {
        Path file = tempDir.resolve("vector.txt");
        Vector expected = new ArrayVector(values);

        new ArrayVectorFileWriter().write(expected, file);
        Vector actual = new ArrayVectorFileReader().read(file);

        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), actual.get(i));
        }
    }

    private static Stream<Arguments> matrices() {
        return Stream.of(
                Arguments.of((Object) new double[][]{{1, 2}, {3, 4}}),
                Arguments.of((Object) new double[][]{{-1.5, 0, 2.25}})
        );
    }

    private static Stream<Arguments> vectors() {
        return Stream.of(
                Arguments.of((Object) new double[]{1, 2, 3}),
                Arguments.of((Object) new double[]{-1.5, 0, 2.25})
        );
    }

    private static void assertMatrixEquals(Matrix expected, Matrix actual) {
        assertEquals(expected.rows(), actual.rows());
        assertEquals(expected.cols(), actual.cols());
        for (int row = 0; row < expected.rows(); row++) {
            for (int column = 0; column < expected.cols(); column++) {
                assertEquals(expected.get(row, column), actual.get(row, column));
            }
        }
    }
}
