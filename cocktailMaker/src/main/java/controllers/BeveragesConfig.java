package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import server.Beverage;
import server.db.DAO;

/**
 * Created by b06514a on 6/10/2017.
 */
public class BeveragesConfig {

    @FXML
    public ListView<Beverage> beverages_list;

    @FXML
    public TextField newBeverageName;

    @FXML
    public Button add_button;

    @FXML
    public Button remove_button;

    public void initialize() {

        ObservableList<Beverage> beverages = FXCollections.observableArrayList(DAO.getInstance().getBeverages());
        beverages_list.setItems(beverages);
        newBeverageName.requestFocus();

        remove_button.setDisable(true);

        beverages_list.getFocusModel().focusedIndexProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue != null) {
                remove_button.setDisable(false);
            }
        }));
        beverages_list.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == false) {
                remove_button.setDisable(true);
            }
        });

        add_button.addEventHandler(ActionEvent.ACTION, event -> {
            Beverage beverage = new Beverage(newBeverageName.getText());
            DAO.getInstance().addBeverage(beverage);
        });
    }

}
