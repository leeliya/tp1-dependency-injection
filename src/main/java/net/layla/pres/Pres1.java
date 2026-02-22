package net.layla.pres;

import net.layla.dao.DaoImpl;
import net.layla.metier.MetierImpl;

public class Pres1 {
    public static void main(String[] args) {
        DaoImpl d = new DaoImpl();
        MetierImpl metier = new MetierImpl();
        metier.setDao(d);
        System.out.println("RES = "+metier.calcul());
    }
}
