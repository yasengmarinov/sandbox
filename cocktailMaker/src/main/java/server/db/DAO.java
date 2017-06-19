package server.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import server.db.entities.Ingredient;
import server.db.entities.Pump;
import server.db.entities.User;

import javax.jws.soap.SOAPBinding;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by b06514a on 6/10/2017.
 */
public class DAO {

    public static final Logger logger = Logger.getLogger(DAO.class.getName());

    private static SessionFactory sessionFactory;
    private static Session session;
    private static CriteriaBuilder criteriaBuilder;

    public static void init() {
        logger.info("Initializing DAO");
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure(DAO.class.getClassLoader().getResource("config/hibernate/hibernate.cfg.xml"))
                .build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        session = sessionFactory.openSession();
        criteriaBuilder = session.getCriteriaBuilder();

    }

    private DAO() {

    }

    private static boolean persist(Object object) {
        try {
            session.beginTransaction();
            session.save(object);
            session.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            session.getTransaction().rollback();
            return false;
        }
    }

    private static boolean update(Object object) {
        try {
            session.beginTransaction();
            session.update(object);
            session.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            session.getTransaction().rollback();
            return false;
        }
    }

    private static boolean delete(Object object) {
        try {
            session.beginTransaction();
            session.delete(object);
            session.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            session.getTransaction().rollback();
            return false;
        }
    }

    public static class Ingredients {

        public static List<Ingredient> getIngredients() {
            Query<Ingredient> query = session.createQuery("from Ingredient ");
            return query.list();
        }

        public static boolean addIngredient(Ingredient ingredient) {
            return persist(ingredient);
        }

        public static boolean removeIngredient(Ingredient ingredient) {
            return delete(ingredient);
        }

        public static boolean updateIngredient(Ingredient ingredient) {
            return update(ingredient);
        }
    }

    public static class Pumps {

        public static List<Pump> getPumps() {
            Query<Pump> query = session.createQuery("from Pump ");
            return query.list();
        }

        public static boolean addPump(Pump pump) {
            return persist(pump);
        }

        public static boolean updatePump(Pump pump) {
            return update(pump);
        }
    }

    public static class Users {
        public static List<User> getUsers() {
            Query<User> query = session.createQuery("from User ");
            return query.list();
        }
        public static boolean addUser(User user) {
            return persist(user);
        }

        public static boolean updateUser(User user) {
            return update(user);
        }

        public static boolean removeUser(User user) {
            return delete(user);
        }
    }
}
