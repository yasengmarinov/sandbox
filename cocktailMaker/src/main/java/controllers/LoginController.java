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
import server.Utils;
import server.session.Session;
import server.session.SessionManager;

public class LoginController {

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
                PageNavigator.navigateTo(PageNavigator.PAGE_HOME_ADMIN);
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

}
