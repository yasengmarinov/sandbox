package server.db;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import server.Utils;
import server.db.entities.Ingredient;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * Created by b06514a on 6/10/2017.
 */
public class DAO {

    public static final String DB_LOCATION_PROPERTY = "hibernate.connection.url";

    private static DAO instance;

    private static SessionFactory sessionFactory;
    private static Session session;
    private static CriteriaBuilder criteriaBuilder;

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
        session = sessionFactory.openSession();
        criteriaBuilder = session.getCriteriaBuilder();
    }

    public static void createDb() {
        Properties hibernateProperties = Utils.loadPropertiesFile("hibernate.properties");

        if (Files.exists(Paths.get(hibernateProperties.getProperty(DB_LOCATION_PROPERTY).replace("jdbc:derby:", "")))) {
            System.out.println("DB Exists!");
            return;
        } else {
            System.out.println("Creating new DB");
        }

        StringBuilder builder = new StringBuilder();

        builder.append(hibernateProperties.getProperty(DB_LOCATION_PROPERTY));
        builder.append(";");
        builder.append("create=true");

        try {
            Connection connection = DriverManager.getConnection(builder.toString());
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static class Ingredients {

        public static List<Ingredient> getIngredients() {
            Query<Ingredient> query = session.createQuery("from Ingredient ");
            return query.list();
        }

        public static void addIngredient(Ingredient ingredient) {
            session.beginTransaction();
            session.save(ingredient);
            session.getTransaction().commit();
        }

        public static void deleteIngredient(Ingredient ingredient) {
            session.beginTransaction();

            session.delete(ingredient);

            session.getTransaction().commit();
        }
    }

}
