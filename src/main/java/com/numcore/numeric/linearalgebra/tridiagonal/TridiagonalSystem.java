package com.numcore.numeric.linearalgebra.tridiagonal;

public record TridiagonalSystem(double[] lowerDiagonal, double[] mainDiagonal,
                                double[] upperDiagonal, double[] rightHandSide) {
    public TridiagonalSystem {
        lowerDiagonal = lowerDiagonal.clone();
        mainDiagonal = mainDiagonal.clone();
        upperDiagonal = upperDiagonal.clone();
        rightHandSide = rightHandSide.clone();

        int size = mainDiagonal.length;
        if (size == 0) {
            throw new IllegalArgumentException("System must contain at least one equation");
        }
        if (lowerDiagonal.length != size || upperDiagonal.length != size
                || rightHandSide.length != size) {
            throw new IllegalArgumentException("All system arrays must have the same size");
        }
        if (lowerDiagonal[0] != 0 || upperDiagonal[size - 1] != 0) {
            throw new IllegalArgumentException("Values outside the three diagonals must be zero");
        }
    }

    @Override
    public double[] lowerDiagonal() {
        return lowerDiagonal.clone();
    }

    @Override
    public double[] mainDiagonal() {
        return mainDiagonal.clone();
    }

    @Override
    public double[] upperDiagonal() {
        return upperDiagonal.clone();
    }

    @Override
    public double[] rightHandSide() {
        return rightHandSide.clone();
    }
}
