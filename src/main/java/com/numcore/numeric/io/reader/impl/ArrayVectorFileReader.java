package com.numcore.numeric.io.reader.impl;

import com.numcore.numeric.io.reader.VectorFileReader;
import com.numcore.numeric.linearalgebra.vector.Vector;
import com.numcore.numeric.linearalgebra.vector.impl.ArrayVector;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Stream;

public class ArrayVectorFileReader implements VectorFileReader {
    @Override
    public Vector read(Path path) throws IOException {
        double[] data;
        try (Stream<String> lines = Files.lines(path)) {
            data = lines.filter(line -> !line.isBlank())
                    .flatMap(line -> Arrays.stream(line.trim().split("\\s+")))
                    .mapToDouble(Double::parseDouble)
                    .toArray();
        }

        if (data.length == 0) throw new IllegalArgumentException("Vector file is empty");
        return new ArrayVector(data);
    }
}
