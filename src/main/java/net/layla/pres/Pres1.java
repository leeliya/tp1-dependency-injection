package net.layla.pres;

import net.layla.dao.DaoImpl;
import net.layla.metier.MetierImpl;
import net.layla.net.layla.ext.DaoImplV2;

public class Pres1 {
    public static void main(String[] args) {
        DaoImplV2 d = new DaoImplV2();
        MetierImpl metier = new MetierImpl(d);
        //metier.setDao(d);
        System.out.println("RES = "+metier.calcul());
    }
}
