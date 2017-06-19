package controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import server.Utils;
import server.db.DAO;
import server.db.entities.Ingredient;

import java.util.Collections;
import java.util.List;

import static server.Utils.Dialogs.openAlert;

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

    ObservableList<Ingredient> ingredientObservableList = FXCollections.observableArrayList();

    public void initialize() {

        ingredients_list.setItems(ingredientObservableList);
        refreshIngredientsList();

        newIngredientName.requestFocus();

        setButtonsDisableProperty();

        addEventListeners();
    }

    private void setButtonsDisableProperty() {
        BooleanBinding removeButtonEnabled = Bindings.and(ingredients_list.focusedProperty(), ingredients_list.getFocusModel().focusedItemProperty().isNotNull());
        remove_button.disableProperty().bind(removeButtonEnabled.not());

        BooleanBinding addButtonEnabled = Bindings.isNotEmpty(newIngredientName.textProperty());
        add_button.disableProperty().bind(addButtonEnabled.not());
    }

    private void addEventListeners() {
        add_button.addEventHandler(ActionEvent.ACTION, event -> {
             if (DAO.Ingredients.addIngredient(new Ingredient(newIngredientName.getText()))) {
                 newIngredientName.clear();
                 refreshIngredientsList();
                 newIngredientName.requestFocus();
             } else {
                 openAlert(Alert.AlertType.INFORMATION, Utils.Dialogs.TITLE_INCONSISTENT_DATE, "Please make sure the name is unique!");
             }
        });

        remove_button.addEventHandler(ActionEvent.ACTION, event -> {
            if (DAO.Ingredients.removeIngredient(ingredients_list.getFocusModel().getFocusedItem())) {
                refreshIngredientsList();
            } else {
                openAlert(Alert.AlertType.WARNING, Utils.Dialogs.TITLE_DELETE_FAILED,
                        "Please make sure the ingredient is not used in a pump or cocktail");
            }
        });
    }

    private void refreshIngredientsList() {
        List<Ingredient> list = DAO.Ingredients.getIngredients();
        Collections.sort(list);
        ingredientObservableList.clear();
        ingredientObservableList.addAll(list);
    }

}
