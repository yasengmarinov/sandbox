package controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import server.db.entities.Ingredient;
import server.db.DAO;

/**
 * Created by b06514a on 6/10/2017.
 */
public class Ingredients {

    @FXML
    public ListView<Ingredient> ingredients_list;

    @FXML
    public TextField newIngredientName;

    @FXML
    public Button add_button;

    @FXML
    public Button remove_button;

    public void initialize() {

        ObservableList<Ingredient> ingredients = FXCollections.observableArrayList(DAO.getInstance().getIngredients());
        ingredients_list.setItems(ingredients);
        newIngredientName.requestFocus();

        BooleanBinding removeButtonEnabled = Bindings.and(ingredients_list.focusedProperty(), ingredients_list.getFocusModel().focusedItemProperty().isNotNull());
        remove_button.disableProperty().bind(removeButtonEnabled.not());

        add_button.addEventHandler(ActionEvent.ACTION, event -> {
//            Ingredient ingredient = new Ingredient(newBeverageName.getText());
//            DAO.getInstance().addBeverage(ingredient);
        });
    }

}
