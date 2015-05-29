package com.logotet.util;


/**
 * Klasa koja simulira bit mapu, tj. binarno predstavljanje broja
 */
public class BitMap {
    private static final int SIZE = 12;
    private int[] niz;

    public BitMap() {
        niz = new int[SIZE];
        for (int i = 0; i < SIZE; i++)
            niz[i] = 0;
    }

    /**
     * postavlja i-ti bit
     */
    public void set(int i) {
        if ((i < SIZE) && (i > 0))
            niz[i] = 1;
    }

    /**
     * brise  i-ti bit
     */
    public void clear(int i) {
        if ((i < SIZE) && (i > 0))
            niz[i] = 0;
    }

    /**
     * testira da li je i-ti bit postavljen
     */

    public boolean testSet(int i) {
        if ((i >= SIZE) || (i < 0))
            return false;
        if (niz[i] == 1)
            return true;
        else
            return false;
    }


    /**
     * Na osnovi int argumenta postavja sve bitove koji odgovaraju tom broju;Zapravo,
     * pravi binarnu prezentaciju broja;
     */
    public void putValue(int broj) {
        for (int i = 1; i < SIZE; i++) {
            niz[i] = broj % 2;
            broj /= 2;
            if (broj == 0)
                break;
        }
    }

    /**
     * Vraca vrednost na osnovu setovanih bitova, tj. binarnu rezentaciju pretvara u broj
     */
    public int getValue() {
        int broj = 0;
        int power = 1;
        for (int i = 1; i < SIZE; i++) {
            if (niz[i] == 1)
                broj += power;
            power *= 2;
        }
        return broj;
    }
	
/*	public int getMaxSet(){
		if((i >= SIZE) || (i < 0))
			return 0;
		if(niz[i] == 1)
			return i;
		else
			return 0;
	}
*/
    /****/
    public int getSize() {
        return SIZE;
    }

    public String toString() {
        String s = getValue() + "*";
        for (int i = SIZE - 1; i > 0; i--)
            s += niz[i];
        return s;
    }


    public static void main(String[] args) {
        int broj = Integer.parseInt(args[0]);
        BitMap bm = new BitMap();
        bm.putValue(broj);
        System.out.println("" + bm);
    }
}