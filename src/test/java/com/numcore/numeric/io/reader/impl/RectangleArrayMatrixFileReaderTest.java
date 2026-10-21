package com.numcore.numeric.io.reader.impl;

import com.numcore.numeric.linearalgebra.matrix.Matrix;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RectangleArrayMatrixFileReaderTest {
    @TempDir
    Path tempDir;

    @Test
    void readsVariantSevenMatrixWithDecimalCommas() throws IOException {
        Path file = tempDir.resolve("matrix.txt");
        Files.writeString(file, """
                22,5575 39,3926 3,5581 28,0089 -30,8114
                -30,2615 29,7423 34,8813 12,8698 49,4996
                11,6921 -24,9337 28,8091 21,3882 14,7635
                -41,8493 35,5320 -19,2518 -2,7015 -32,5864
                """);

        Matrix matrix = new RectangleArrayMatrixFileReader().read(file);

        assertEquals(4, matrix.rows());
        assertEquals(5, matrix.cols());
        assertEquals(22.5575, matrix.get(0, 0));
        assertEquals(28.0089, matrix.get(0, 3));
        assertEquals(-30.8114, matrix.get(0, 4));
        assertEquals(-30.2615, matrix.get(1, 0));
        assertEquals(49.4996, matrix.get(1, 4));
        assertEquals(11.6921, matrix.get(2, 0));
        assertEquals(14.7635, matrix.get(2, 4));
        assertEquals(-41.8493, matrix.get(3, 0));
        assertEquals(-2.7015, matrix.get(3, 3));
        assertEquals(-32.5864, matrix.get(3, 4));
    }
}
