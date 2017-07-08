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
import server.db.entities.*;

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

    public static <T> List<T> getAll(Class<T> clazz) {
        Criteria criteria = session.createCriteria(clazz);
        return criteria.list();
    }

    private static void prepopulateData() {
        if (DAL.getAll(User.class).size() == 0)
            DAL.persist(new User("admin", Utils.md5("admin"), true));
    }

    public static boolean persist(Object object) {
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

    public static boolean update(Object object) {
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

    public static boolean delete(Object object) {
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

    public static User getUser(String username, String password) {
        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.eq("username", username.toLowerCase()));
        criteria.add(Restrictions.eq("password", Utils.md5(password)));
        return criteria.list().isEmpty() ? null : (User) criteria.list().get(0);
    }

    public static boolean addHistoryEntry(int type, String message) {
        return persist(new HistoryLog(type, message));
    }

    public static List<Cocktail_Ingredient> getCocktailIngredients(Cocktail cocktail) {
        Criteria criteria = session.createCriteria(Cocktail_Ingredient.class);
        criteria.add(Restrictions.eq("cocktail", cocktail));
        return criteria.list().isEmpty() ? null : criteria.list();
    }

    public static Pump getPumpByIngredient(Ingredient ingredient) {
        Criteria criteria = session.createCriteria(Pump.class);
        criteria.add(Restrictions.eq("ingredient", ingredient));
        criteria.add(Restrictions.eq("enabled", true));
        return criteria.list().isEmpty() ? null : (Pump) criteria.list().get(0);
    }

}
