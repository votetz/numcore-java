package com.numcore.numeric.io.reader.impl;

import com.numcore.numeric.io.reader.MatrixFileReader;
import com.numcore.numeric.linearalgebra.matrix.Matrix;
import com.numcore.numeric.linearalgebra.matrix.impl.RectangleArrayMatrix;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class RectangleArrayMatrixFileReader implements MatrixFileReader {
    @Override
    public Matrix read(Path path) throws IOException {
        List<double[]> rows;
        try (var lines = Files.lines(path)) {
            rows = lines.filter(line -> !line.isBlank())
                    .map(String::trim)
                    .map(RectangleArrayMatrixFileReader::parseRow)
                    .toList();
        }

        if (rows.isEmpty()) throw new IllegalArgumentException("Matrix file is empty");
        int columns = rows.get(0).length;
        if (rows.stream().anyMatch(row -> row.length != columns)) {
            throw new IllegalArgumentException("Matrix rows have different lengths");
        }
        return new RectangleArrayMatrix(rows.toArray(double[][]::new));
    }

    private static double[] parseRow(String line) {
        String[] values = line.split("\\s+");
        double[] row = new double[values.length];
        for (int column = 0; column < values.length; column++) {
            row[column] = parseNumber(values[column]);
        }
        return row;
    }

    static double parseNumber(String value) {
        return Double.parseDouble(value.replace(',', '.'));
    }
}
