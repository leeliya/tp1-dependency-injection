package net.layla.pres;

import net.layla.metier.MetierImpl;

public class Pres1 {
    public static void main(String[] args) {
        MetierImpl metier = new MetierImpl();
        System.out.println("RES = "+metier.calcul());
    }
}
