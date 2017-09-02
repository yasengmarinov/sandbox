package cocktailMaker.ui.controllers;

import cocktailMaker.server.PageNavigator;
import cocktailMaker.server.Utils;
import cocktailMaker.server.db.entities.User;
import cocktailMaker.server.session.Session;
import cocktailMaker.server.session.SessionManager;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import org.apache.log4j.Logger;

public class LoginController {

    private static final Logger logger = Logger.getLogger(LoginController.class.getName());

    @FXML
    public GridPane main_grid;

    @FXML
    public HBox credentials_box;

    @FXML
    public TextField username_field;

    @FXML
    public PasswordField password_field;

    @FXML
    public Button login_button;

    @FXML
    public Button logWithCredentials_button;

    @FXML
    public Button logWithCard_button;

    protected BooleanProperty credentials_mode = new SimpleBooleanProperty(false);

    public void initialize() {
        BooleanBinding loginButtonEnabled = Bindings.and(username_field.textProperty().isNotEmpty(),
                password_field.textProperty().isNotEmpty());

        login_button.disableProperty().bind(loginButtonEnabled.not());
        credentials_box.visibleProperty().bind(credentials_mode);
        logWithCredentials_button.visibleProperty().bind(credentials_mode.not());

        logWithCredentials_button.addEventHandler(ActionEvent.ACTION, event -> {
            credentialsModeToggle();
        });
        logWithCard_button.addEventHandler(ActionEvent.ACTION, event -> {
            credentialsModeToggle();
        });

        login_button.addEventHandler(ActionEvent.ACTION, event -> {
            Session session = SessionManager.createSesssion(username_field.getText(), password_field.getText());
            if (session != null) {
                logUser(session.getUser());
            } else {
                Utils.Dialogs.openAlert(Alert.AlertType.WARNING, Utils.Dialogs.TITLE_LOGIN_FAILED, Utils.Dialogs.CONTENT_LOGIN_FAILED);
                username_field.clear();
                password_field.clear();
            }
        });

        main_grid.addEventFilter(KeyEvent.KEY_TYPED, event -> {
//            System.out.println(event.getCharacter());
        });


    }

    private void credentialsModeToggle() {
        credentials_mode.setValue(!credentials_mode.getValue());
    }

    private void logUser(User user) {
        if (user == null) {
            logger.warn("User is null");
            return;
        }
        if (user.getIsAdmin()) {
            PageNavigator.navigateTo(PageNavigator.PAGE_HOME_ADMIN);
        } else {
            PageNavigator.navigateTo(PageNavigator.PAGE_MAKE_COCKTAIL);
        }
    }

}
