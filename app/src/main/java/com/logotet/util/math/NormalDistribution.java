package com.logotet.util.math;

import com.logotet.util.MainConverter;

/**
 *
 */
public class NormalDistribution {

    /**
     * najbolja je aproksimacija 3, ostale su tu reda radi...
     */
    public static double getApprox1(double z) {
        double a1 = 0.9999998582;
        double a2 = 0.487385796;
        double a3 = 0.02109811045;
        double a4 = 0.003372948927;
        double a5 = -0.00005172897742;
        double a6 = 0.0000856957942;
        double zagrada = a1 + a2 * z + a3 * z * z + a4 * z * z * z + a5 * z * z * z * z + a6 * z * z * z * z * z;
        double tmp = Math.pow(zagrada, -16.00);
        return z = 1.0 - 0.5 * tmp;
    }

    public static double getApprox2(double z) {
        double tmp = 0.5 - 0.398942 * z - 0.066490 * z * z * z + 0.09974 * z * z * z * z * z;
        return tmp;
    }

    public static double getApprox3(double z) {
        double y = 0.7988 * z * (1 + 0.04417 * z * z);

        double up = Math.exp(2 * y);
        double down = 1.0 + up;
        return up / down;
    }

    public static double getApprox4(double z) {
        double up = 562 + z * (351 + 83 * z);
        double down = 165 + 703 / z;

        double tmp = 1.0 - 0.5 * Math.exp((-1) * up / down);
        return tmp;
    }

    public static double getApprox5(double z) {
        double stepen = Math.sqrt(Math.PI / 8.00);
        double stp2 = stepen * z * z * (-1.0);
        double koren = 1.0 - Math.exp(stp2);
        return 0.5 + 0.5 * Math.sqrt(koren);
    }

    public static double getProbByZ(double z) {
        return getApprox3(z);
    }

    public static double getProbability(double z) {
        if (z < 0.0)
            return 1.0 - getProbByZ((-1) * z);
        return getProbByZ(z);
    }

    public static double getProbability(double a, double b) {
        if ((a >= 0.0) & (b >= 0.0)) {
            double p1 = getProbability(Math.min(a, b));
            double p2 = getProbability(Math.max(a, b));
            return Math.abs(p2 - p1);
        }

        if ((a <= 0.0) & (b <= 0.0)) {
            double p1 = 1.0 -  getProbability(Math.min(a, b));
            double p2 = 1.0 - getProbability(Math.max(a, b));
            return Math.abs(p2 - p1);
        }

        if ((a >= 0.0) & (b <= 0.0)) {
            double p1 = getProbability(b);
            double p2 = getProbability(a);
            return Math.abs(p2 - p1);
        }

//        if ((a <= 0.0) & (b >= 0.0)) {
        double p1 = getProbability(b);
        double p2 = getProbability(a);
        return Math.abs(p2 - p1);

    }

    public static void main(String[] args) {
        double rnd = Math.random() * 3.0;
        for (int i = 0; i < 26; i++) {

            rnd = i * 0.1;
            System.out.println(MainConverter.doubleToString(rnd, 1) + "\t" + getProbByZ(rnd));
//           System.out.println(MainConverter.doubleToString(rnd,1) + "\t" + getApprox3(rnd));
//              getApprox1(rnd) + "\n\t\t\t\t\t" +
//              "2. " + getApprox2(rnd) + "\n\t\t\t\t\t" +
//              "3. " +  );
//              "4. " + getApprox4(rnd) + "\n\t\t\t\t\t" +
//              "5. " + getApprox5(rnd));
        }


    }

}
