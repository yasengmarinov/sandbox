package controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import server.db.DAO;
import server.db.entities.Ingredient;

import java.util.stream.Collectors;

/**
 * Created by b06514a on 6/10/2017.
 */
public class IngredientsController {

    @FXML
    public ListView<Ingredient> ingredients_list;

    @FXML
    public TextField newIngredientName;

    @FXML
    public Button add_button;

    @FXML
    public Button remove_button;

    public void initialize() {

//        ingredients_list.setItems(
//                FXCollections.observableArrayList(DAO.Ingredients.getIngredients().
//                stream().
//                map(Ingredient::getName).
//                collect(Collectors.toList())));

        ingredients_list.setItems(FXCollections.observableArrayList(DAO.Ingredients.getIngredients()));
        newIngredientName.requestFocus();

        BooleanBinding removeButtonEnabled = Bindings.and(ingredients_list.focusedProperty(), ingredients_list.getFocusModel().focusedItemProperty().isNotNull());
        remove_button.disableProperty().bind(removeButtonEnabled.not());

        add_button.addEventHandler(ActionEvent.ACTION, event -> {
            DAO.Ingredients.addIngredient(new Ingredient(newIngredientName.getText()));
            newIngredientName.clear();
            refreshIngredientsList();
            newIngredientName.requestFocus();
        });

        remove_button.addEventHandler(ActionEvent.ACTION, event -> {
            DAO.Ingredients.deleteIngredient(ingredients_list.getFocusModel().getFocusedItem());
            refreshIngredientsList();
        });
    }

    private void refreshIngredientsList() {
//        ingredients_list.setItems(FXCollections.observableArrayList(DAO.Ingredients.getIngredients().
//                stream().
//                map(Ingredient::getName).
//                collect(Collectors.toList())));
        ingredients_list.setItems(FXCollections.observableArrayList(DAO.Ingredients.getIngredients()))
        ;

    }

}
