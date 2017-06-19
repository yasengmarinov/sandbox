package server;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by B06514A on 6/19/2017.
 */
public class PageNavigator {

    public static final String PAGE_CONFIGURE_PUMPS = "views/configurePumps.fxml";
    public static final String PAGE_CONFIGURE_USERS = "views/configureUsers.fxml";
    public static final String PAGE_CONFIGURE_INGREDIENTS = "views/configureIngredients.fxml";

    public static final String PAGE_HOME_ADMIN = "views/home_admin.fxml";
    public static final String PAGE_LOGIN = "views/login.fxml";

    private static Stage stage;

    public static void init(Stage stage) {
        PageNavigator.stage = stage;
    }

    public static void navigateTo(String page) {
        try {
            Parent root = FXMLLoader.load(ServerLauncher.class.getClassLoader().getResource(page));
            stage.setScene(new Scene(root, 800, 480));
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }
}
