package server.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import server.db.objects.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * Created by b06514a on 6/10/2017.
 */
public class DAO {

    public static final String DB_LOCATION_PROPERTY = "db.location";

    private static DAO instance;

    private static SessionFactory sessionFactory;

    public static DAO getInstance() {
        if (instance == null)
            instance = new DAO();
        return instance;
    }

    private DAO() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure(DAO.class.getClassLoader().getResource("config/hibernate/hibernate.cfg.xml"))
                .build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

        //TODO delete these
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

      Cocktail cocktail = session.load(Cocktail.class, 1);
        for (Cocktail_Ingredient cocktail_ingredient : cocktail.getIngredients())
            System.out.println(cocktail_ingredient.getMillilitres());
        transaction.commit();

        session.close();
    }

    List<Ingredient> ingredients = new LinkedList<Ingredient>();

    public static void createDb(Properties properties) {
        if (Files.exists(Paths.get(properties.getProperty(DB_LOCATION_PROPERTY)))) {
            System.out.println("DB Exists!");
            return;
        }

        StringBuilder builder = new StringBuilder();

        builder.append("jdbc:derby:");
        builder.append(properties.getProperty(DB_LOCATION_PROPERTY));
        builder.append(";");
        builder.append("create=true");


        try {
            Connection connection = DriverManager.getConnection(builder.toString());
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addBeverage(Ingredient ingredient) {

        instance.ingredients.add(ingredient);
        System.out.println("Added ingredient: " + ingredient);
        return true;
    }

    public List<Ingredient> getIngredients() {

        return ingredients;
    }

}
