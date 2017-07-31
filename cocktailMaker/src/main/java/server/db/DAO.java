package server.db;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.criterion.Restrictions;
import server.LogType;
import server.Utils;
import server.db.entities.*;

import java.util.List;

/**
 * Created by b06514a on 6/10/2017.
 */
public class DAO {

    public static final Logger logger = Logger.getLogger(DAO.class.getName());

    private static SessionFactory sessionFactory;
    private static Session session;

    private DAO() {

    }

    public static void init() {
        logger.info("Initializing DAO");
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure(DAO.class.getClassLoader().getResource("config/hibernate/hibernate.cfg.xml"))
                .build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        session = sessionFactory.openSession();
        prepopulateData();
    }

    public static <T> List<T> getAll(Class<T> clazz) {
        Criteria criteria = session.createCriteria(clazz);
        return criteria.list();
    }

    private static void prepopulateData() {
        if (DAO.getAll(User.class).size() == 0)
            DAO.persist(new User("admin", Utils.md5("admin"), true));
    }

    public static boolean persist(Object object) {
        logger.info("Persisting object " + object + " in the DB");
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
        logger.info("Updating object " + object);
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
        logger.info("Deleting object " + object);
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

    public static User getUser(String username) {
        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.eq("username", username.toLowerCase()));
        return criteria.list().isEmpty() ? null : (User) criteria.list().get(0);
    }

    @SuppressWarnings("deprecation")
    public static User getUser(String username, String password) {
        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.eq("username", username.toLowerCase()));
        criteria.add(Restrictions.eq("password", Utils.md5(password)));
        return criteria.list().isEmpty() ? null : (User) criteria.list().get(0);
    }

    public static boolean addHistoryEntry(int type, String message) {
        return persist(new HistoryLog(type, message));
    }

    public static List<CocktailIngredient> getCocktailIngredients(Cocktail cocktail) {
        Criteria criteria = session.createCriteria(CocktailIngredient.class);
        criteria.add(Restrictions.eq("cocktail", cocktail));
        return criteria.list().isEmpty() ? null : criteria.list();
    }

    public static Dispenser getDispenserByIngredient(Ingredient ingredient) {
        Criteria criteria = session.createCriteria(Dispenser.class);
        criteria.add(Restrictions.eq("ingredient", ingredient));
        criteria.add(Restrictions.eq("enabled", true));
        return criteria.list().isEmpty() ? null : (Dispenser) criteria.list().get(0);
    }

    public synchronized static Dispenser getDispenserByCocktailIngredient(CocktailIngredient cocktailIngredient) {
        Criteria criteria = session.createCriteria(Dispenser.class);
        criteria.add(Restrictions.eq("ingredient", cocktailIngredient.getIngredient()));
        criteria.add(Restrictions.eq("enabled", true));
        criteria.add(Restrictions.ge("millilitresLeft", cocktailIngredient.getMillilitres()));
        return criteria.list().isEmpty() ? null : (Dispenser) criteria.list().get(0);
    }

    public static List<Dispenser> getEnabledDispensers() {
        Criteria criteria = session.createCriteria(Dispenser.class);
        criteria.add(Restrictions.eq("enabled", true));
        return criteria.list();
    }

    public static List<HistoryLog> getAdminLog() {
        Criteria criteria = session.createCriteria(HistoryLog.class);
        criteria.add(Restrictions.not(Restrictions.in("type", LogType.getCocktailEvents())));
        return criteria.list();
    }

    public static List<HistoryLog> getCocktailLog() {
        Criteria criteria = session.createCriteria(HistoryLog.class);
        criteria.add(Restrictions.in("type", LogType.getCocktailEvents()));
        return criteria.list();
    }

    public static List<Cocktail> getCocktailsByGroup(CocktailGroup cocktailGroup) {
        Criteria criteria = session.createCriteria(Cocktail.class);
        criteria.add(Restrictions.eq("cocktailGroup", cocktailGroup));
        return criteria.list();
    }

}
