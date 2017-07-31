package server.session;

import server.db.DAO;
import server.db.entities.User;

/**
 * Created by B06514A on 6/22/2017.
 */
public class SessionManager {

    private static Session session = null;

    public static final Session createSesssion(String username, String password) {

        if (username.isEmpty() || password.isEmpty())
            return null;
        User user = DAO.getUser(username, password);

        if (user != null) {
            session = new Session(user);
        }
        return session;
    }

    public static final Session getSession() {
        return session;
    }

    public static void sessionInvalidate() {
        session = null;
    }
}
