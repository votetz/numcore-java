package com.numcore.numeric.io.reader.impl;

import com.numcore.numeric.io.reader.MatrixFileReader;
import com.numcore.numeric.linearalgebra.matrix.Matrix;
import com.numcore.numeric.linearalgebra.matrix.impl.RectangleArrayMatrix;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class RectangleArrayMatrixFileReader implements MatrixFileReader {
    @Override
    public Matrix read(Path path) throws IOException {
        List<double[]> rows;
        try (Stream<String> lines = Files.lines(path)) {
            rows = lines.filter(line -> !line.isBlank())
                    .map(String::trim)
                    .map(line -> Arrays.stream(line.split("\\s+"))
                            .mapToDouble(Double::parseDouble)
                            .toArray())
                    .toList();
        }

        if (rows.isEmpty()) throw new IllegalArgumentException("Matrix file is empty");
        int columns = rows.get(0).length;
        if (rows.stream().anyMatch(row -> row.length != columns)) {
            throw new IllegalArgumentException("Matrix rows have different lengths");
        }
        return new RectangleArrayMatrix(rows.toArray(double[][]::new));
    }
}
