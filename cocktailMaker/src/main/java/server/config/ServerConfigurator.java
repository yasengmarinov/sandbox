package server.config;

import server.db.DAO;

import java.util.Properties;

/**
 * Created by B06514A on 6/15/2017.
 */
public class ServerConfigurator {

    private static ServerConfigurator instance;
    private Properties properties;

    public static ServerConfigurator getInstance() {
        if (instance == null) {
            instance = new ServerConfigurator();
        }
        return instance;
    }

    public void configure() {
        DAO.createDb(properties);
        DAO.getInstance();
    }

    public final Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
