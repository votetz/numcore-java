package com.numcore.numeric.io.writer.impl;

import com.numcore.numeric.io.writer.MatrixFileWriter;
import com.numcore.numeric.linearalgebra.matrix.Matrix;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RectangleArrayMatrixFileWriter implements MatrixFileWriter {
    @Override
    public void write(Matrix matrix, Path path) throws IOException {
        var lines = IntStream.range(0, matrix.rows())
                .mapToObj(row -> IntStream.range(0, matrix.cols())
                        .mapToObj(column -> Double.toString(matrix.get(row, column)))
                        .collect(Collectors.joining(" ")))
                .toList();
        Files.write(path, lines);
    }
}
