package com.logotet.util.math;

/**
 * LeastSquares
 * Created by : ?
 * Date: Jun 13, 2005
 */
public class LeastSquares {
    Matrix matA;
    Matrix matB;

    public LeastSquares(Matrix matA, Matrix matB) throws MatrixDimException {
        Matrix transA = matA.getTransponed();
        this.matA = transA.getMultipliedBy(matA);
        this.matB = transA.getMultipliedBy(matB);
    }

    public Matrix getSolution() throws NoSolutionException, MatrixDimException {
        LinearEquation le = new LinearEquation(matA, matB);
        return le.getSolution();
    }

    public static void main(String[] args) {
        double[][] mat1 = {{1, 0},
                           {0, 1},
                           {1, 1}};
        double[][] mat2 = {{1},
                           {2},
                           {2}};
        Matrix A = new Matrix(mat1);
        Matrix B = new Matrix(mat2);
        try {
            LeastSquares ls = new LeastSquares(A, B);
            Matrix rez = null;
            rez = ls.getSolution();
            System.out.println("M:" + rez);
        } catch (NoSolutionException e) {
            System.out.println("Greska NO SLUTION");
        } catch (MatrixDimException e) {
            System.out.println("Greska");
        }
    }
}
