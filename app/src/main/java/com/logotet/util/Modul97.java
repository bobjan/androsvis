package com.logotet.util;

import java.math.BigInteger;

/**
 * Klasa za rad sa modulom 97
 * 
 * 
 */
public class Modul97 {
    public static String getControl(long broj) {
        return getControl(String.valueOf(broj));
    }

    public static String getControl(String broj) {
        StringBuffer sb = new StringBuffer(broj);

        BigInteger big98 = new BigInteger("98");
        BigInteger bignum = new BigInteger(broj);
        bignum = bignum.multiply(new BigInteger("100"));
        BigInteger rem = bignum.remainder(new BigInteger("97"));
        big98 = big98.subtract(rem);
        String rezultat = big98.toString();
        if (rezultat.length() == 1)
            rezultat = "0" + rezultat;
        return rezultat;
    }

    public static void main(String[] args) {
        System.out.println(Modul97.getControl(args[0]));
    }

}
