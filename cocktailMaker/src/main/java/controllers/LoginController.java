package controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import server.PageNavigator;

public class LoginController {

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
            PageNavigator.navigateTo(PageNavigator.PAGE_HOME_ADMIN);
        });

    }

}
