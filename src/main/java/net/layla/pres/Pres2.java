package net.layla.pres;

import net.layla.dao.IDao;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Pres2 {
    public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        Scanner scanner = new Scanner(new File("config.txt"));
        String daoClassName = scanner.nextLine();
        Class cDao = Class.forName(daoClassName);
        IDao dao= (IDao) cDao.newInstance();
        System.out.println(dao.getData());

    }
}
