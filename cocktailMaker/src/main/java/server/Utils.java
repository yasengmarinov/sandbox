package server;

import javafx.scene.control.Alert;

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

    public static class Dialogs {

        public static final String TITLE_INCONSISTENT_DATE = "Inconsistent Data";
        public static final String TITLE_DELETE_FAILED = "Delete Failed";

        public static void openAlert(Alert.AlertType alertType, String title, String content) {
           openAlert(alertType, title, null, content);
        }

        public static void openAlert(Alert.AlertType alertType, String title, String header, String content) {
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.showAndWait();
        }
    }
}
