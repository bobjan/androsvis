package com.logotet.util;

/**
 * Klasa koja cuva neki opseg da bi kontrolisala kretanje napred nazad.
 * Ima mogucnost definisanja koraka, ili prihvatanja koraka od 20. Ako se instancira
 * pomocu konstruktora sa argumentom <I>korak</I>, onda se vrednost koraka ne moze vise menjati.
 * Ako se pak instancira bez argumenata, ukoliko se ne zeli default <I>korak</I> 20
 * potrebno je odmah definisati korak pomocu setKorak(int) metode, u suprotnom prvo kretanje napred-nazad(gore-dole)
 * ce spreciti naknadno menjanje vrednosti koraka
 */
public class Range {
    private int donjaGranica;
    private int gornjaGranica;
    private boolean mozeGore;
    private boolean mozeDole;

    private int korak;
    private boolean korakSet;
    private boolean upLimitSet;

    private long upLimitValue;

    /**
     * Konstruktor koji podrazumeva default vrednost 20
     */
    public Range() {
        this(20);
        korakSet = false;
    }

    /**
     * Konstruktor koji kao argument koristi broj koraka
     */
    public Range(int korak) {
        this.korak = korak;
        donjaGranica = 1;
        gornjaGranica = korak;
        mozeGore = true;
        mozeDole = false;
        upLimitValue = 999999999999L;
        upLimitSet = false;
        korakSet = true;
    }

    /**
     * Promena vrednosti koraka, ako je koriscen konstruktor bez argumenata. Mora biti pozvana pre bilo kog kretanja
     */
    public void setKorak(int broj) {
        if (!korakSet) {
            korak = broj;
            gornjaGranica = korak;
            korakSet = true;
        }
    }

    /**
     * Menja opsege za vrednost koraka kada je kretanje unapred ( ka sledecem )
     */
    public void goUp() {
        korakSet = true;
        if (mozeGore) {
            donjaGranica += korak;
            gornjaGranica = donjaGranica + korak - 1;
            mozeDole = true;
        }

        if (upLimitSet)
            if (gornjaGranica > upLimitValue) {
                gornjaGranica = (int) upLimitValue;
                mozeGore = false;
            }
    }

    /**
     * Menja opsege za vrednost koraka kada je kretanje unazad ( ka prethodnom )
     */
    public void goDown() {
        korakSet = true;
        mozeGore = true;
        if (mozeDole) {
            donjaGranica -= korak;
            if (donjaGranica <= 1)
                donjaGranica = 1;
            gornjaGranica = donjaGranica + korak - 1;
            if (donjaGranica <= 1)
                mozeDole = false;
            if (gornjaGranica == (int) upLimitValue)
                mozeGore = false;
        }
    }

    /**
     * vraca true ako se moze jos ici unapred (ka sledecem)
     */
    public boolean canGoUp() {
        return mozeGore;
    }

    /**
     * vraca true ako se moze jos ici unazad (ka prethodnom)
     */
    public boolean canGoDown() {
        return mozeDole;
    }

    /**
     * vraca vrednost gornje granice
     */
    public int getGornjaGranica() {
        return gornjaGranica;
    }

    /**
     * vraca vrednost donje granice
     */
    public int getDonjaGranica() {
        return donjaGranica;
    }

    /**
     * vraca vrednost koraka
     */
    public int getKorak() {
        return korak;
    }


    /**
     * kada se pretrazivanjem unapred doslo do kraja,
     * onda je potrebno saopstiti ovoj instanci koliko je u zadnjem paketu bilo podataka.
     */
    public void setUpLimit(int broj) {
        korakSet = true;
        if (broj >= korak)
            broj = korak - 1;
        if (broj < 0)
            broj = korak - 1;
        gornjaGranica = donjaGranica + broj - 1;
        upLimitValue = gornjaGranica;
        mozeGore = false;
        upLimitSet = true;
    }

    /**
     * Ponistava prethodno postavljen limit gornje granice, i gornja granica je opet mnoooogooo velika
     */
    public void unsetUpLimit() {
        korakSet = true;
        upLimitValue = 999999999999L;
        mozeGore = true;
        upLimitSet = false;
    }

    public static void main(String[] args) {
        byte[] b = new byte[80];
        int limit;
        boolean pravac, end;
        String gore, dole;
        Range rang = new Range();
        try {
            if (args.length == 1) {
                int x = Integer.parseInt(args[0]);
                System.out.println("\nMenjam korak na  " + x);
                rang.setKorak(x);
            }

        } catch (NumberFormatException nfe) {
        }
        while (true) {
            limit = 0;
            pravac = true;
            end = false;
            dole = "-";
            gore = "-";
            if (rang.canGoDown())
                dole = "+";
            if (rang.canGoUp())
                gore = "+";

            System.out.print(dole + gore + " " + rang.getDonjaGranica() + " - " + rang.getGornjaGranica());
            int broj = 0;
            try {
                broj = System.in.read(b);
            } catch (java.io.IOException ioe) {
                broj = 0;
            }

            switch (broj) {
                case 5:
                    limit = ((int) b[1] - 48) * 10 + ((int) b[2] - 48);
                    if (b[0] == '-')
                        pravac = false;
                    end = true;
                    break;
                case 4:
                    limit = ((int) b[1] - 48);
                    if (b[0] == '-')
                        pravac = false;
                    end = true;
                    break;
                case 3:
                    if (b[0] == '-')
                        pravac = false;
                    break;
                default:
                    break;
            }
            if (end)
                rang.setUpLimit(limit);
            else if (pravac)
                rang.goUp();
            else
                rang.goDown();
            if (limit == 99)
                break;
        }
    }

}