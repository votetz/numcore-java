package com.numcore.numeric.io.writer.impl;

import com.numcore.numeric.io.writer.VectorFileWriter;
import com.numcore.numeric.linearalgebra.vector.Vector;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.IntStream;

public class ArrayVectorFileWriter implements VectorFileWriter {
    @Override
    public void write(Vector vector, Path path) throws IOException {
        var lines = IntStream.range(0, vector.size())
                .mapToObj(index -> Double.toString(vector.get(index)))
                .toList();
        Files.write(path, lines);
    }
}
