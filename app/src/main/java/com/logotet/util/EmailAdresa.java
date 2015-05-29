package com.logotet.util;


/**
 * Static metoda za proveru validnosti email adrese
 *
 * @author Boban Jankovic
 * @version 1.0
 * @see
 */
public class EmailAdresa {

    /**
     * Testira ispravnost email adrese
     *
     * @param adresa - email adresa koja se testira
     * @return boolean - true if valid; otherxies false
     *         <p/>
     *         *
     */
    public static boolean ok(String adresa) {
        int pocetak = 0;
        int brojac = 0;
        while (pocetak < adresa.length()) {
            int poz = adresa.indexOf("@", pocetak + 1);
            pocetak = poz;
            if (poz < 0)
                pocetak = adresa.length() + 1;
            if (poz == 0)
                return false;
            if (poz == adresa.length() - 1)
                return false;
            if (poz > 0)
                brojac++;

        }
        if (brojac != 1)
            return false;
        return true;

    }


    public static void main(String[] args) {
        String[] tekst = {"aao.co@m", "bbb@dddd.com", "bbb@bbb@bbb", "bbbb@"};
        for (int i = 0; i < tekst.length; i++) {
            System.out.println(tekst[i] + " - " + ok(tekst[i]));
        }

    }
}		