package server.db;

import server.Beverage;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by b06514a on 6/10/2017.
 */
public class DAO {

    public static List<Beverage> getBeverages() {
        List<Beverage> beverages = new LinkedList<Beverage>();

        beverages.add(new Beverage("Orange juice"));
        beverages.add(new Beverage("Tequila"));
        beverages.add(new Beverage("Vodka"));

        return beverages;
    }

}
