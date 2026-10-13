package com.numcore.numeric.io.reader;

import com.numcore.numeric.linearalgebra.vector.Vector;

import java.io.IOException;
import java.nio.file.Path;

public interface VectorFileReader {
    Vector read(Path path) throws IOException;
}
