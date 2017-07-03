package controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import server.LogType;
import server.Utils;
import server.db.DAL;
import server.db.entities.Ingredient;

import java.util.Collections;
import java.util.List;

import static server.Utils.Dialogs.openAlert;

/**
 * Created by b06514a on 6/10/2017.
 */
public class ConfigureIngredientsController {

    @FXML
    public ListView<Ingredient> ingredients_list;

    @FXML
    public TextField newIngredientName;

    @FXML
    public Button add_button;

    @FXML
    public Button remove_button;

    @FXML
    public Button edit_button;

    @FXML
    public Button cancel_button;

    ObservableList<Ingredient> ingredientObservableList = FXCollections.observableArrayList();
    SimpleBooleanProperty editMode = new SimpleBooleanProperty(false);

    public void initialize() {

        ingredients_list.setItems(ingredientObservableList);
        refreshIngredientsList();

        newIngredientName.requestFocus();

        setButtonsVisibility();

        addEventListeners();
    }

    private void setButtonsVisibility() {
        add_button.textProperty().bind(new StringBinding() {
            {
                super.bind(editMode);
            }
            @Override
            protected String computeValue() {
                return (editMode.getValue() ? "Save" : "Add");
            }
        });
        cancel_button.visibleProperty().bind(editMode);
        ingredients_list.disableProperty().bind(editMode);

        BooleanBinding removeButtonEnabled = Bindings.and(ingredients_list.focusedProperty(), ingredients_list.getFocusModel().focusedItemProperty().isNotNull());
        remove_button.disableProperty().bind(removeButtonEnabled.not());

        BooleanBinding addButtonEnabled = Bindings.isNotEmpty(newIngredientName.textProperty());
        add_button.disableProperty().bind(addButtonEnabled.not());

        edit_button.disableProperty().bind(removeButtonEnabled.not());

    }

    private void addEventListeners() {
        add_button.addEventHandler(ActionEvent.ACTION, event -> {
            boolean success;
            if (editMode.getValue()) {
                Ingredient ingredient = ingredients_list.getFocusModel().getFocusedItem();
                ingredient.setName(newIngredientName.getText());
                success = DAL.Ingredients.updateIngredient(ingredient);
            } else {
                success = DAL.Ingredients.addIngredient(new Ingredient(newIngredientName.getText()));
            }
            if (success) {
                DAL.Log.addEntry(LogType.TYPE_CREATE_INGREDIENT, "New ingredient added: " + newIngredientName.getText());
                newIngredientName.clear();
                refreshIngredientsList();
                editMode.set(false);
            } else {
                openAlert(Alert.AlertType.INFORMATION, Utils.Dialogs.TITLE_INCONSISTENT_DATA, "Please make sure the name is unique!");
            }
        });

        remove_button.addEventHandler(ActionEvent.ACTION, event -> {
            if (DAL.Ingredients.removeIngredient(ingredients_list.getFocusModel().getFocusedItem())) {
                DAL.Log.addEntry(LogType.TYPE_REMOVE_INGREDIENT,
                        "Removed ingredient: " + ingredients_list.getFocusModel().getFocusedItem().getName());
                refreshIngredientsList();
            } else {
                openAlert(Alert.AlertType.WARNING, Utils.Dialogs.TITLE_DELETE_FAILED,
                        "Please make sure the ingredient is not used in a pump or cocktail");
            }
        });

        edit_button.addEventHandler(ActionEvent.ACTION, event -> {
            newIngredientName.setText(ingredients_list.getFocusModel().getFocusedItem().getName());
            editMode.set(true);
        });

        cancel_button.addEventHandler(ActionEvent.ACTION, event -> {
            newIngredientName.clear();
            editMode.set(false);
        });
    }

    private void refreshIngredientsList() {
        List<Ingredient> list = DAL.Ingredients.getIngredients();
        Collections.sort(list);
        ingredientObservableList.clear();
        ingredientObservableList.addAll(list);
    }

}
