package controllers;

import controllers.templates.SimpleAddRemovePage;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import server.db.DAL;
import server.db.entities.Cocktail;
import server.db.entities.CocktailGroup;
import server.db.entities.Cocktail_Ingredient;
import server.db.entities.Ingredient;

import javax.swing.*;
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

    @FXML
    public Button deleteIngredient_button;

    private ObservableList<Cocktail_Ingredient> ingredientsObservableList = FXCollections.observableArrayList();

    public void initialize() {
        super.initialize();
        configureCocktailGroupDropdown();
        configureIngredientsTable();
        configureAddRemoveIngredientButtons();
    }

    private void configureCocktailGroupDropdown() {
        List<CocktailGroup> cocktailGroups = DAL.getAll(CocktailGroup.class);
        cocktailGroup_box.setItems(FXCollections.observableArrayList(cocktailGroups));
    }

    private void configureAddRemoveIngredientButtons() {
        addIngredient_button.disableProperty().bind(isObjectSelected().not());
        addIngredient_button.addEventHandler(ActionEvent.ACTION, event -> {
            Cocktail_Ingredient cocktail_ingredient = getCocktailIngredientByDialog();
            if (cocktail_ingredient != null) {
                DAL.persist(cocktail_ingredient);
                refreshIngredientsTable();
            }
        });

        deleteIngredient_button.disableProperty().bind(ingredients_table.getSelectionModel().selectedItemProperty().isNull());
        deleteIngredient_button.addEventHandler(ActionEvent.ACTION, event -> {
            DAL.delete(ingredients_table.getSelectionModel().getSelectedItem());
            refreshIngredientsTable();
        });
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

    private void configureIngredientsTable() {
        refreshIngredientsTable();
        ingredients_table.setItems(ingredientsObservableList);
    }

    private void refreshIngredientsTable() {
        List<Cocktail_Ingredient> list = DAL.getCocktailIngredients
                ((Cocktail)object_table.getSelectionModel().getSelectedItem());
        ingredientsObservableList.clear();
        if (list != null) {
            ingredientsObservableList.addAll(list);
            FXCollections.sort(ingredientsObservableList);
        }
        ingredients_table.refresh();
    }

    private Cocktail_Ingredient getCocktailIngredientByDialog() {

        Dialog<Cocktail_Ingredient> dialog = addCocktailIngredientDialog();

        Optional<Cocktail_Ingredient> newCocktailIngredient = dialog.showAndWait();
        return newCocktailIngredient.get();
    }

    private Dialog<Cocktail_Ingredient> addCocktailIngredientDialog() {
        Cocktail selectedCocktail =  (Cocktail) object_table.getSelectionModel().getSelectedItem();
        Dialog<Cocktail_Ingredient> dialog = new Dialog<>();
        dialog.setTitle("Add Ingredient");
        dialog.setHeaderText("Add a new ingredient for cocktail " + selectedCocktail);

        ComboBox<Ingredient> ingredient = new ComboBox<>();
        ingredient.setPrefWidth(200);
        ingredient.setPromptText("Ingredient");
        ingredient.setItems(FXCollections.observableArrayList(DAL.getAll(Ingredient.class)));

        TextField ingredientAmount = new TextField();
        ingredientAmount.setMaxWidth(150);
        ingredientAmount.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d+")) {
                ingredientAmount.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        ingredientAmount.setPromptText("Amount in ml");

        VBox vBox = new VBox();
        vBox.getChildren().add(ingredient);
        vBox.getChildren().add(ingredientAmount);

        dialog.getDialogPane().setContent(vBox);

        ButtonType buttonAdd = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        dialog.getDialogPane().getButtonTypes().addAll(buttonCancel, buttonAdd);
        dialog.getDialogPane().lookupButton(buttonAdd).disableProperty().bind(
                Bindings.and(ingredient.valueProperty().isNotNull(), ingredientAmount.textProperty().isNotEmpty()).not()
        );
        dialog.setResultConverter(param -> {
            if (param == buttonAdd) {
                return new Cocktail_Ingredient(selectedCocktail, ingredient.getValue(), Integer.valueOf(ingredientAmount.getText()));
            }

            return null;
        });
        return dialog;
    }

}
