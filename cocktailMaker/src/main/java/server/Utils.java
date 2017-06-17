package server;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * Created by B06514A on 6/17/2017.
 */
public class Utils {

    public static Properties loadPropertiesFile(String fileName) {
        URL serverPropertiesURL = Utils.class.
                getClassLoader().getResource(fileName);
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(serverPropertiesURL.getFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties;
    }

}
