package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    public TextField username_field;

    @FXML
    public TextField password_field;

    @FXML
    public Button login_button;

    public void initialize() {
        System.out.println("Im ALIVE");
        login_button.addEventHandler(ActionEvent.ACTION, event -> {
            System.out.println("this works too");
            Main.navigateTo("page2");
        });
    }

    public void loginAction(ActionEvent event) {
        System.out.println(username_field.getText());
        System.out.println(password_field.getText());
    }

}
