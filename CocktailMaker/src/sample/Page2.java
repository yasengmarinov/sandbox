package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Created by yasen on 6/7/17.
 */
public class Page2 {

    @FXML
    public Button back_button;

    public void initialize() {
        back_button.addEventHandler(ActionEvent.ACTION, event -> {Main.navigateTo("sample");});
    }

}
