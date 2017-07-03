package server.session;

import server.db.entities.User;

/**
 * Created by B06514A on 6/22/2017.
 */
public class Session {

    private User user;

    public Session(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
