package com.logotet.util;


/**
 * Unoverzalni generator kombinacija
 * Ne znam kada sam ga i kako napravio ali sada kada sam ga pronasao u svojoj biblioteci radi!!
 * *
 */

public class CombinationGenerator {
    private int n;
    private int k;
    private int[] current;
    private int[] max;
    private int pivot;
    private long numberOf;


    public CombinationGenerator(int n, int k) {
        this.n = n;
        this.k = k;
        current = new int[k];
        max = new int[k];
        for (int i = 0; i < k; i++) {
            current[i] = i;
            max[i] = n - k + i - 1;// it is 1 less than real maximum because of next() implementation
        }
        pivot = k - 1;
        numberOf = countNumberOf(n, k);
    }

    public boolean next() {
        if (current.length == 0)
            return false;
        if (current[0] > max[0])
            return false;

        current[pivot]++;

        for (int i = pivot + 1; i < k; i++) {
            current[i] = current[i - 1] + 1;
            pivot = k - 1;
        }
        while ((pivot >= 0) && (current[pivot] > max[pivot])) {
            pivot -= 1;
        }
        return true;
    }

    public int[] getCurrent() {
        return current;
    }

    public long getNumberOf() {
        return numberOf;
    }

    public static long countNumberOf(int n, int k) {
        int numberOf = 1;
        for (int i = 1; i <= k; i++) {
            numberOf *= n + 1 - i;
            numberOf /= i;
        }
        return numberOf;
    }

    public static void main(String[] args) {
        int n = 0;
        int k = 0;
        try {
            n = Integer.parseInt(args[0]);
            k = Integer.parseInt(args[1]);
            if (n < k) {
                System.out.println("n must be >= k");
                System.exit(0);
            }
        } catch (Exception e) {
            System.out.println("Enter arguments: n k");
            System.exit(0);
        }

        CombinationGenerator cg = new CombinationGenerator(n, k);
        int[] comb;
        System.out.println("ukupno = " + cg.getNumberOf() + " kombinacija");

        do {
            comb = cg.getCurrent();
            for (int i = 0; i < comb.length; i++)
                System.out.print(" " + comb[i]);
            System.out.println("  od " + cg.getNumberOf() + " -");
        } while (cg.next());
    }
}