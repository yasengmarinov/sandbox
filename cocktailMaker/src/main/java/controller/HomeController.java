package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class HomeController {

    @FXML
    public TextField username_field;

    public void initialize() {
        System.out.println("Home screen loaded");
        username_field.setText("kor");
    }


}
