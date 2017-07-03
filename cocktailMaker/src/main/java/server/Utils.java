package server;

import javafx.beans.binding.StringBinding;
import javafx.scene.control.Alert;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
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

    public static String md5(String string) {
        String hash;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            BigInteger bigInteger = new BigInteger(1, md.digest(string.getBytes()));
            hash = bigInteger.toString(16);
        } catch (NoSuchAlgorithmException e) {
            hash = "";
            e.printStackTrace();
        }
        return hash;
    }

    public static class Dialogs {

        public static final String TITLE_INCONSISTENT_DATA = "Inconsistent Data";
        public static final String TITLE_DELETE_FAILED = "Delete Failed";
        public static final String TITLE_PASSWORD_DO_NOT_MATCH = "Passwords do not match";
        public static final String TITLE_LOGIN_FAILED = "Login failed";

        public static final String CONTENT_PASSWORDS_DO_NOT_MATCH = "The Password is different from the Confirm Password";
        public static final String CONTENT_LOGIN_FAILED = "Username or password are incorrect";

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
