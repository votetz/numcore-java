package com.numcore.numeric.io.reader;

import com.numcore.numeric.linearalgebra.matrix.Matrix;

import java.io.IOException;
import java.nio.file.Path;

public interface MatrixFileReader {
    Matrix read(Path path) throws IOException;
}
