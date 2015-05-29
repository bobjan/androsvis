package com.logotet.util.math;

/**
 * Matrix
 * Created by : ?
 * Date: Jun 12, 2005
 */
public class Matrix {
    double[][] matrica;
    int red;
    int kol;

    public Matrix(double[][] matrica) {
        this.matrica = matrica;
        this.red = matrica.length;
        this.kol = matrica[0].length;
    }

    public Matrix(int red, int kol) {
        this.red = red;
        this.kol = kol;
        matrica = new double[red][kol];
        for (int i = 0; i < red; i++) {
            for (int j = 0; j < kol; j++) {
                matrica[i][j] = 0.0;
            }
        }
    }

    public Matrix(double[] niz) {
        matrica = new double[niz.length][1];
        red = niz.length;
        kol = 1;
        for (int i = 0; i < niz.length; i++) {
            matrica[i][0] = niz[i];
        }
    }

    public double[][] getMatrica() {
        return matrica;
    }

    public double getField(int i, int j) {
        return matrica[i][j];
    };


    public void swapRows(int one, int two) {
        for (int j = 0; j < kol; j++) {
            double tmp = matrica[one][j];
            matrica[one][j] = matrica[two][j];
            matrica[two][j] = tmp;
        }
    }

    public int getRed() {
        return red;
    }

    public int getKol() {
        return kol;
    }

    public void multiply(double skalar) {
        for (int i = 0; i < red; i++)
            for (int j = 0; j < kol; j++) {
                matrica[i][j] *= skalar;
            }
    }

    public Matrix getMultipliedBy(Matrix mat) throws MatrixDimException {
        if (kol != mat.getRed())
            throw new MatrixDimException();
        double[][] tmp = mat.getMatrica();
        Matrix nova = new Matrix(red, mat.getKol());
        double[][] rez = nova.getMatrica();

        for (int i = 0; i < red; i++) {
            for (int j = 0; j < mat.getKol(); j++) {
                for (int k = 0; k < kol; k++) {
                    rez[i][j] += matrica[i][k] * tmp[k][j];

                }
            }
        }
        return new Matrix(rez);
    }

    public Matrix getTransponed() {
        double[][] trans = new double[kol][red];
        for (int i = 0; i < red; i++) {
            for (int j = 0; j < kol; j++) {
                trans[j][i] = matrica[i][j];
            }
        }
        return new Matrix(trans);
    }

    public String toString() {
        String s = "";
        for (int i = 0; i < red; i++) {
            s += "\n";
            for (int j = 0; j < kol; j++) {
                s += "\t" + matrica[i][j];
            }
        }
        return s;
    }

    public static void main(String[] args) {
        double[][] mat1 = {{3, 1, -2},
                           {5, 7, 4}};

        double[][] mat2 = {{8, 5},
                           {2, 1},
                           {3, 4}};
        double[][] mat3 = {{3, 1}, {2, 4}};
        double[][] mat4 = {{1, 2}, {3, 5}};
        Matrix mA = new Matrix(mat1);
        Matrix mB = new Matrix(mat2);
        Matrix mC = new Matrix(mat3);
        Matrix mD = new Matrix(mat4);
        Matrix rez = null;
        try {
            rez = mA.getTransponed().getMultipliedBy(mC);
            System.out.println("M:" + rez);
        } catch (MatrixDimException e) {
            System.out.println("Greska");
        }
        System.exit(0);
    }

}
