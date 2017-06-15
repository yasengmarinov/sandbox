package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import server.ServerLauncher;

public class LoginController {

    @FXML
    public TextField username_field;

    @FXML
    public PasswordField password_field;

    @FXML
    public Button login_button;

    public void initialize() {

        login_button.addEventHandler(ActionEvent.ACTION, event -> {
            ServerLauncher.navigateTo("home_admin");
        });

    }


}
