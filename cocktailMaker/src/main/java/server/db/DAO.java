package server.db;

import server.Beverage;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by b06514a on 6/10/2017.
 */
public class DAO {

    private static DAO instance;

    public static DAO getInstance() {
        if (instance == null)
            instance = new DAO();
        return instance;
    }

    List<Beverage> beverages = new LinkedList<Beverage>();

    private DAO() {

        beverages.add(new Beverage("Orange juice"));
        beverages.add(new Beverage("Tequila"));
        beverages.add(new Beverage("Vodka"));
    }

    public boolean addBeverage(Beverage beverage) {

        instance.beverages.add(beverage);
        System.out.println("Added beverage: " + beverage);
        return true;
    }

    public List<Beverage> getBeverages() {

        return beverages;
    }

}
