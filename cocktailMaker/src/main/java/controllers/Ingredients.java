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
<<<<<<< HEAD:cocktailMaker/src/main/java/controllers/Ingredients.java
import server.db.entities.Ingredient;
=======
import server.db.objects.Ingredient;
>>>>>>> 37f76f34ce4e92301e5a759bf4b7d8ff0bfe645f:cocktailMaker/src/main/java/controllers/BeveragesConfig.java
import server.db.DAO;

/**
 * Created by b06514a on 6/10/2017.
 */
public class Ingredients {

    @FXML
<<<<<<< HEAD:cocktailMaker/src/main/java/controllers/Ingredients.java
    public ListView<Ingredient> ingredients_list;
=======
    public ListView<Ingredient> beverages_list;
>>>>>>> 37f76f34ce4e92301e5a759bf4b7d8ff0bfe645f:cocktailMaker/src/main/java/controllers/BeveragesConfig.java

    @FXML
    public TextField newIngredientName;

    @FXML
    public Button add_button;

    @FXML
    public Button remove_button;

    public void initialize() {

        ObservableList<Ingredient> ingredients = FXCollections.observableArrayList(DAO.getInstance().getIngredients());
<<<<<<< HEAD:cocktailMaker/src/main/java/controllers/Ingredients.java
        ingredients_list.setItems(ingredients);
        newIngredientName.requestFocus();
=======
        beverages_list.setItems(ingredients);
        newBeverageName.requestFocus();
>>>>>>> 37f76f34ce4e92301e5a759bf4b7d8ff0bfe645f:cocktailMaker/src/main/java/controllers/BeveragesConfig.java

        BooleanBinding removeButtonEnabled = Bindings.and(ingredients_list.focusedProperty(), ingredients_list.getFocusModel().focusedItemProperty().isNotNull());
        remove_button.disableProperty().bind(removeButtonEnabled.not());

        add_button.addEventHandler(ActionEvent.ACTION, event -> {
<<<<<<< HEAD:cocktailMaker/src/main/java/controllers/Ingredients.java
//            Ingredient ingredient = new Ingredient(newBeverageName.getText());
//            DAO.getInstance().addBeverage(ingredient);
=======
            Ingredient ingredient = new Ingredient(newBeverageName.getText());
            DAO.getInstance().addBeverage(ingredient);
>>>>>>> 37f76f34ce4e92301e5a759bf4b7d8ff0bfe645f:cocktailMaker/src/main/java/controllers/BeveragesConfig.java
        });
    }

}
