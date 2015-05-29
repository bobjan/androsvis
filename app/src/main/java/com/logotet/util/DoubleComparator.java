package com.logotet.util;

import java.util.Calendar;
import java.util.Comparator;

/**
 * Comparator koji se zasniva na poredjenju double vrednosti
 *
 */
public class DoubleComparator implements Comparator {
    private int smer;	// ako je negativan - desc, pozitivan - asc

    public DoubleComparator(int ascdesc) {
        smer = ascdesc;
    }

    public DoubleComparator() {
        this(1);
    }

    public int compare(Object obj1, Object obj2) {
        DoubleComparable dat1 = (DoubleComparable) obj1;
        DoubleComparable dat2 = (DoubleComparable) obj2;

        double tmp1 = dat1.getDoubleValue();
        double tmp2 = dat2.getDoubleValue();


        int tmp = 0;
        if(tmp1 > tmp2)
        tmp = 10;
        if(tmp1 < tmp2)
            tmp = -10;
        return tmp * smer;
    }

    public boolean equals(Object obj) {
        return false;
    }
}