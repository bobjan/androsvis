package com.logotet.util.math;

/**
 * LinearEquation
 * Created by : ?
 * Date: Jun 12, 2005
 */
public class LinearEquation {
    Matrix matA;
    Matrix matB;

    public LinearEquation(Matrix matricaA, Matrix matricaB) {
        matA = matricaA;
        matB = matricaB;
        matB.multiply(-1.0);
    }

    public Matrix getSolution() throws MatrixDimException, NoSolutionException {
        if (matA.getRed() != matB.getRed())
            throw new MatrixDimException();
        double[][] a = matA.getMatrica();
        double[][] b = matB.getMatrica();
        int pivotRed, pivotKol;
        for (pivotRed = 0; pivotRed < matA.getRed(); pivotRed++) {
            if (a[pivotRed][pivotRed] == 0) {
                a[pivotRed][pivotRed] = 0.00001;        // ovo je nekorektno, ali pomaze da radi!!??
//                int nextRow = 0;
//                nextRow = findNextRow(pivotRed);
//                matA.swapRows(pivotRed, nextRow);
//                matB.swapRows(pivotRed, nextRow);
            }
            pivotKol = pivotRed; // radi testa
            double pivot = a[pivotRed][pivotKol];

            a[pivotRed][pivotKol] = 1;
            //           System.out.println("Pivot je izabran:" + pivotRed + ", " + pivotKol + " = " + pivot);

            // svi ostali ...
            for (int i = 0; i < matA.getRed(); i++) {
                if (i != pivotRed) {
                    b[i][0] = b[i][0] * pivot - a[i][pivotKol] * b[pivotRed][0];
                    for (int j = 0; j < matA.getKol(); j++) {
                        if (j != pivotKol) {
                            a[i][j] = a[i][j] * pivot - a[i][pivotKol] * a[pivotRed][j];
                        }
                    }
                }
            }
            //         System.out.println("Ostatak je odradjen:" + matA + "\n=== " + matB.getTransponed() + "= = = = = ");


            // svi iz istog reda menjaju znak
            for (int j = 0; j < matA.getKol(); j++)
                if (j != pivotKol)
                    a[pivotRed][j] *= -1.0;
            b[pivotRed][0] *= -1.0;
            //       System.out.println("Promenjen je  znak..." + matA + "\n=== " + matB.getTransponed() + "= = = = = ");
            // svi se dele sa pivotom
            for (int i = 0; i < matA.getRed(); i++) {
                b[i][0] /= pivot;
                for (int j = 0; j < matA.getKol(); j++) {
                    a[i][j] /= pivot;
                }
            }
            //          System.out.println("Sve je podeljeno sa pivotom..!!!!!!!!." + matA + "\n=== " + matB.getTransponed() + "= = = = = ");
        }
        return new Matrix(b);
    }

    private int findNextRow(int pivotRed) throws NoSolutionException {
        int tmp = pivotRed + 1;
        while (tmp < matA.getRed()) {
            if (matA.getField(tmp, tmp) != 0)
                return tmp;
        }
        throw new NoSolutionException();
    }

    public static void main(String[] args) {
        double[][] mat1 = {{1, 1, 1, 1, 1, 1},
//                           {2,0,3,0,0,2},
                           {0, 1, 1, 0, 0, 2},
                           {1, 0, 0, 2, 2, 0},
                           {3, 2, 1, 2, 0, 0},
                           {3, 5, -1, -1, -1, 0},
                           {1, 1, 0, 0, 2, 4}};
        double[][] mat2 = {{15.5},
                           //                         {12},
                           {6},
                           {19},
                           {18}, {1}, {15}};
        Matrix A = new Matrix(mat1);
        Matrix B = new Matrix(mat2);
        LinearEquation le = new LinearEquation(A, B);
        try {
            Matrix rez = null;
            rez = le.getSolution();
            System.out.println("M:" + rez);
        } catch (NoSolutionException e) {
            System.out.println("Greska NO SLUTION");

        } catch (MatrixDimException e) {
            System.out.println("Greska");
        }
    }
}
