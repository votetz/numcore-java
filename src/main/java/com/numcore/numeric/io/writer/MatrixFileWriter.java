package com.numcore.numeric.io.writer;

import com.numcore.numeric.linearalgebra.matrix.Matrix;

import java.io.IOException;
import java.nio.file.Path;

public interface MatrixFileWriter {
    void write(Matrix matrix, Path path) throws IOException;
}
