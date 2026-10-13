package com.numcore.numeric.io.writer;

import com.numcore.numeric.linearalgebra.vector.Vector;

import java.io.IOException;
import java.nio.file.Path;

public interface VectorFileWriter {
    void write(Vector vector, Path path) throws IOException;
}
