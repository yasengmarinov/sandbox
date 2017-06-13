package controllers;

import javafx.beans.property.IntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import server.Beverage;
import server.db.DAO;

/**
 * Created by b06514a on 6/10/2017.
 */
public class BeveragesConfig {

    @FXML
    public ListView<Beverage> beverages_list;

    @FXML
    public Button add_button;

    @FXML
    public Button remove_button;

    public void initialize() {
        ObservableList<Beverage> beverages = FXCollections.observableArrayList(DAO.getBeverages());
        beverages_list.setItems(beverages);

        beverages_list.focusedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                System.out.println(oldValue);
            }
        });
    }

}
