package com.logotet.util;

import java.util.Calendar;
import java.util.Comparator;

/**
 * Comparator koji se zasniva na poredjenju datuma pod pretpostavkom da
 * objekti koji se porede imaju metodu getStringKey();
 */
public class StringBasedComparator implements Comparator {
    private int smer;	// ako je negativan - desc, pozitivan - asc

    public StringBasedComparator(int ascdesc) {
        smer = ascdesc;
    }

    public StringBasedComparator() {
        this(1);
    }

    public int compare(Object obj1, Object obj2) {
        StringComparable dat1 = (StringComparable) obj1;
        StringComparable dat2 = (StringComparable) obj2;

        String tmp1 = dat1.getStringKey();
        String tmp2 = dat2.getStringKey();
        int tmp = tmp1.compareToIgnoreCase(tmp2);
        return tmp * smer;
    }

    public boolean equals(Object obj) {
        return false;
    }
}