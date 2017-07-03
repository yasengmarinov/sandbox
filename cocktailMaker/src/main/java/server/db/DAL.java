package server.db;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import server.LogType;
import server.Utils;
import server.db.entities.HistoryLog;
import server.db.entities.Ingredient;
import server.db.entities.Pump;
import server.db.entities.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by b06514a on 6/10/2017.
 */
public class DAL {

    public static final Logger logger = Logger.getLogger(DAL.class.getName());

    private static SessionFactory sessionFactory;
    private static Session session;
    private static CriteriaBuilder criteriaBuilder;

    public static void init() {
        logger.info("Initializing DAL");
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure(DAL.class.getClassLoader().getResource("config/hibernate/hibernate.cfg.xml"))
                .build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        session = sessionFactory.openSession();
        criteriaBuilder = session.getCriteriaBuilder();
        prepopulateData();
    }

    private DAL() {

    }

    private static <T> List<T> getAll(Class clazz) {
        Criteria criteria = session.createCriteria(clazz);
        return criteria.list();
    }

    private static void prepopulateData() {
        if (DAL.Users.getUsers().size() == 0)
            DAL.Users.addUser(new User("admin", Utils.md5("admin"), true));
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

        public static User getUser(String username, String password) {
            Criteria criteria = session.createCriteria(User.class);
            criteria.add(Restrictions.eq("username", username));
            criteria.add(Restrictions.eq("password", Utils.md5(password)));
            return criteria.list().isEmpty() ? null : (User) criteria.list().get(0);
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

    public static class Log {
        public static List<HistoryLog> getLog() {
            Query<HistoryLog> query = session.createQuery("from HistoryLog ");
            return query.list();
        }

        public static List<HistoryLog> getConfigLog() {
            Criteria criteria = session.createCriteria(HistoryLog.class);
            criteria.add(Restrictions.not(Restrictions.in("type", LogType.cocktailEvents())));
            return criteria.list();
        }

        public static List<HistoryLog> getCocktailLog() {
            Criteria criteria = session.createCriteria(HistoryLog.class);
            criteria.add(Restrictions.in("type", LogType.cocktailEvents()));
            return criteria.list();
        }

        public static boolean addEntry(HistoryLog historyLog) {
            return persist(historyLog);
        }

        public static boolean addEntry(int type, String message) {
            return persist(new HistoryLog(type, message));
        }
    }

}
