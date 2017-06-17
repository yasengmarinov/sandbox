package server.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import server.db.entities.*;

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

        Ingredient vodka = new Ingredient();
        vodka.setName("vodka");

        CocktailGroup cocktailGroup = new CocktailGroup();
        cocktailGroup.setName("Group3");

        Cocktail cocktail = new Cocktail();
        cocktail.setName("New Cocktail with vodka");
        cocktail.setCocktailGroup(cocktailGroup);

        Cocktail_Ingredient cocktail_ingredient = new Cocktail_Ingredient();
        cocktail_ingredient.setCocktail(cocktail);
        cocktail_ingredient.setIngredient(vodka);
        cocktail_ingredient.setMillilitres(30);

        cocktail.getCocktailIngredients().add(cocktail_ingredient);

        session.save(vodka);
        session.save(cocktailGroup);
        session.save(cocktail);
        session.save(cocktail_ingredient);

        transaction.commit();
        session.close();
    }

    List<Ingredient> ingredients = new LinkedList<Ingredient>();

    public static void createDb(Properties properties) {
//        if (Files.exists(Paths.get(properties.getProperty(DB_LOCATION_PROPERTY)))) {
//            System.out.println("DB Exists!");
//            return;
//        }
//
//        StringBuilder builder = new StringBuilder();
//
//        builder.append("jdbc:derby:");
//        builder.append(properties.getProperty(DB_LOCATION_PROPERTY));
//        builder.append(";");
//        builder.append("create=true");
//
//
//        try {
//            Connection connection = DriverManager.getConnection(builder.toString());
//            connection.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
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
