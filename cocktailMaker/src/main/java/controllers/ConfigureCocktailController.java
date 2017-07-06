package controllers;

import controllers.templates.SimpleAddRemovePage;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import server.db.DAL;
import server.db.entities.Cocktail;
import server.db.entities.CocktailGroup;
import server.db.entities.Cocktail_Ingredient;
import server.db.entities.Ingredient;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by b06514a on 7/5/2017.
 */
public class ConfigureCocktailController extends SimpleAddRemovePage {

    @FXML
    public TableColumn<Cocktail, String> cocktail_column;

    @FXML
    public TableColumn<Cocktail, CocktailGroup> cocktailGroup_column;

    @FXML
    public ComboBox<CocktailGroup> cocktailGroup_box;

    @FXML
    public TableView<Cocktail_Ingredient> ingredients_table;

    @FXML
    public TableColumn<Cocktail_Ingredient, String> ingredientName_column;

    @FXML
    public TableColumn<Cocktail_Ingredient, String> ingredientMl_column;

    @FXML
    public Button addIngredient_button;

    public void initialize() {
        super.initialize();
        populateIngredientsDropdown();
        configureAddIngredientButton();
    }

    private void configureAddIngredientButton() {
        addIngredient_button.disableProperty().bind(isObjectSelected().not());

        addIngredient_button.addEventHandler(ActionEvent.ACTION, event -> {
            openAddIngredientDialog();
        });
    }

    private void openAddIngredientDialog() {
        Cocktail selectedCocktail =  (Cocktail) object_table.getSelectionModel().getSelectedItem();
        Dialog<Cocktail_Ingredient> dialog = new Dialog<>();
        dialog.setTitle("Add Ingredient");
        dialog.setHeaderText("Add a new ingredient for cocktail " + selectedCocktail);

        ComboBox<Ingredient> ingredient = new ComboBox<>();
        ingredient.setPrefWidth(200);
        ingredient.setPromptText("Cocktail Group");
        ingredient.setItems(FXCollections.observableArrayList(DAL.getAll(Ingredient.class)));

        TextField ingredientAmount = new TextField();
        ingredientAmount.setPromptText("Amount in ml");

        VBox vBox = new VBox();
        vBox.getChildren().add(ingredient);
        vBox.getChildren().add(ingredientAmount);

        dialog.getDialogPane().setContent(vBox);

        ButtonType buttonAdd = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        dialog.getDialogPane().getButtonTypes().addAll(buttonCancel, buttonAdd);

        dialog.setResultConverter(param -> {
            if (param == buttonAdd) {
                return new Cocktail_Ingredient(selectedCocktail, ingredient.getValue(), Integer.valueOf(ingredientAmount.getText()));
            }

            return null;
        });

        Optional<Cocktail_Ingredient> newCocktailIngredient = dialog.showAndWait();
        System.out.println(newCocktailIngredient);
    }

    @Override
    protected void configureTableColumns() {
        cocktail_column.setCellValueFactory(new PropertyValueFactory<Cocktail, String>("name"));
        cocktailGroup_column.setCellValueFactory(new PropertyValueFactory<Cocktail, CocktailGroup>("cocktailGroup"));

        ingredientName_column.setCellValueFactory(new PropertyValueFactory<Cocktail_Ingredient, String>("ingredient"));
        ingredientMl_column.setCellValueFactory(param -> {
            SimpleStringProperty simpleStringProperty = new SimpleStringProperty(param.getValue().getMillilitres() + " ml");
            return simpleStringProperty;
        });
    }

    @Override
    protected boolean addObject() {
        return DAL.persist(new Cocktail(newObjectName.getText(), cocktailGroup_box.getSelectionModel().getSelectedItem()));
    }

    @Override
    protected boolean updateObject() {
        Cocktail cocktail = (Cocktail) object_table.getSelectionModel().getSelectedItem();
        cocktail.setName(newObjectName.getText());
        cocktail.setCocktailGroup(cocktailGroup_box.getValue());
        return DAL.update(cocktail);
    }

    @Override
    protected void setClass() {
        this.T = Cocktail.class;
    }

    @Override
    protected BooleanBinding isReadyForAdd() {
        return Bindings.isNotEmpty(newObjectName.textProperty()).
                and(Bindings.isNotNull(cocktailGroup_box.valueProperty()));
    }

    @Override
    protected void clearInputFields() {
        newObjectName.clear();
        cocktailGroup_box.setValue(null);
    }

    private void populateIngredientsDropdown() {
        List<CocktailGroup> list = DAL.getAll(CocktailGroup.class);
        Collections.sort(list);
        cocktailGroup_box.setItems(FXCollections.observableArrayList(list));
    }
}
