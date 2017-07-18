package controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import server.PageNavigator;
import server.ServerLauncher;
import server.Utils;
import server.db.DAL;
import server.db.entities.User;
import server.session.Session;
import server.session.SessionManager;

import java.util.logging.Level;
import org.apache.log4j.Logger;

public class LoginController {

    private static final Logger logger = Logger.getLogger(LoginController.class.getName());

    @FXML
    public GridPane main_grid;

    @FXML
    public TextField username_field;

    @FXML
    public PasswordField password_field;

    @FXML
    public Button login_button;

    public void initialize() {
        BooleanBinding loginButtonEnabled = Bindings.and(username_field.textProperty().isNotEmpty(),
                password_field.textProperty().isNotEmpty());
        login_button.disableProperty().bind(loginButtonEnabled.not());

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
