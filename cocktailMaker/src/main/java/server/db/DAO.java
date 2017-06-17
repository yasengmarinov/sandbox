package server.db;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import server.db.entities.Ingredient;

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
    }

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

        return true;
    }

    public List<Ingredient> getIngredients() {

        return null;
    }

}
