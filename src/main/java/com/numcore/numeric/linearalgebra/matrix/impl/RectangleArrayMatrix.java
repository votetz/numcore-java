package com.numcore.numeric.linearalgebra.matrix.impl;

import com.numcore.numeric.linearalgebra.matrix.Matrix;
import com.numcore.numeric.linearalgebra.matrix.MatrixIndexValidator;
import com.numcore.numeric.linearalgebra.vector.Vector;
import com.numcore.numeric.linearalgebra.vector.impl.ArrayVector;

public class RectangleArrayMatrix implements Matrix {
    private double[][] data;

    public RectangleArrayMatrix(double[][] data) {
        this.data = data;
    }

    private void checkIndex(int row, int column) {
        MatrixIndexValidator.check(row, column, rows(), cols());
    }

    @Override
    public int rows() {
        return data.length;
    }

    @Override
    public int cols() {
        return data[0].length;
    }

    @Override
    public double get(int row, int col) {
        checkIndex(row, col);
        return data[row][col];
    }

    @Override
    public void set(int row, int col, double value) {
        checkIndex(row, col);
        data[row][col] = value;
    }

    @Override
    public void changeRows(int row1, int row2) {
        checkIndex(row1, 0);
        checkIndex(row2, 0);

        double[] temp = data[row1];
        data[row1] = data[row2];
        data[row2] = temp;
    }


    @Override
    public void changeCols(int col1, int col2) {
        checkIndex(0, col1);
        checkIndex(0, col2);

        for (int i = 0; i < rows(); i++) {
            double temp = data[i][col1];
            data[i][col1] = data[i][col2];
            data[i][col2] = temp;
        }
    }

    @Override
    public void transpose() {
        int rows = data.length;
        int cols = data[0].length;
        double[][] transposed = new double[cols][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                transposed[j][i] = data[i][j];
            }
        }
        data = transposed;
    }

    @Override
    public Vector getRow(int row) {
        checkIndex(row, 0);
        return new ArrayVector(data[row]);
    }

    @Override
    public Vector getCol(int col) {
        checkIndex(0, col);
        double[] columnData = new double[rows()];
        for (int i = 0; i < rows(); i++) {
            columnData[i] = data[i][col];
        }
            return new ArrayVector(columnData);
    }

    @Override
    public Matrix copy() {
        int rows = rows();
        int cols = cols();
        double[][] newData = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            System.arraycopy(data[i], 0, newData[i], 0, cols);
        }
        return new RectangleArrayMatrix(newData);
    }
}
