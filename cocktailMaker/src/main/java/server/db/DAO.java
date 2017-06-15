package server.db;

import server.objects.Beverage;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * Created by b06514a on 6/10/2017.
 */
public class DAO {

    public static final String DB_LOCATION_PROPERTY = "db.location";

    private static DAO instance;
    private static Connection connection;

    public static DAO getInstance() {
        if (instance == null)
            instance = new DAO();
        return instance;
    }

    private DAO() {

    }

    List<Beverage> beverages = new LinkedList<Beverage>();

    public static void initialize(Properties properties) {
        StringBuilder builder = new StringBuilder();
        builder.append("jdbc:derby:");
        builder.append(properties.getProperty(DB_LOCATION_PROPERTY));

        boolean newDb = false;
        if (!Files.exists(Paths.get(properties.getProperty(DB_LOCATION_PROPERTY)))) {
            builder.append(";");
            builder.append("create=true");
            newDb = true;
        }

        try {
            connection = DriverManager.getConnection(builder.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (newDb) {
            createDb();
        }
    }

    private static void createDb() {
//        try {
//            connection.
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
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
