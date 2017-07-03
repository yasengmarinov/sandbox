package server.session;

import server.db.DAL;
import server.db.entities.User;

/**
 * Created by B06514A on 6/22/2017.
 */
public class SessionManager {

    private static Session session = null;

    public static final Session createSesssion(String username, String password) {

        if (username.isEmpty() || password.isEmpty())
            return null;
        User user = DAL.Users.getUser(username, password);

        if (user != null) {
            session = new Session(user);
        }
        return session;
    }

    public static Session getSession() {
        return session;
    }

    public static Session createSesssion() {
        return session;
    }

    public static void sessionInvalidate() {
        session = null;
    }
}
