package server;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by B06514A on 6/19/2017.
 */
public class PageNavigator {

    public static final String PAGE_CONFIGURE_DISPENSERS = "views/configureDispensers.fxml";
    public static final String PAGE_CONFIGURE_USERS = "views/configureUsers.fxml";
    public static final String PAGE_CONFIGURE_INGREDIENTS = "views/configureIngredients.fxml";
    public static final String PAGE_CONFIGURE_COCKTAIL_GROUPS = "views/configureCocktailGroups.fxml";
    public static final String PAGE_CONFIGURE_COCKTAILS = "views/configureCocktails.fxml";
    public static final String PAGE_ADMIN_LOG = "views/admin_log.fxml";
    public static final String PAGE_COCKTAIL_LOG = "views/cocktail_log.fxml";
    public static final String PAGE_HOME_ADMIN = "views/home_admin.fxml";
    public static final String PAGE_LOGIN = "views/login.fxml";

    public static final String PAGE_MAKE_COCKTAIL = "views/make_cocktail.fxml";
    public static final String PAGE_REFILL = "views/refill.fxml";

    private static Stage stage;

    public static void init(Stage stage) {
        PageNavigator.stage = stage;
    }

    public static void navigateTo(String page) {
        try {
            Parent root = FXMLLoader.load(ServerLauncher.class.getClassLoader().getResource(page));
            if (stage.getScene() == null) {
                Scene scene = new Scene(root, 800, 480);
                stage.setScene(scene);
            } else {
                stage.getScene().setRoot(root);
            }
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

}
