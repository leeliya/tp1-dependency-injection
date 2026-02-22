package net.layla.dao;

import org.springframework.stereotype.Component;

@Component("d")
public class DaoImpl implements IDao {
    @Override
    public double getData() {
        System.out.println("Version de base de données");
        double t = 34;
        return t;
    }
}
