package controllers;

import controllers.templates.SimpleAddRemovePage;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import server.db.DAL;
import server.db.entities.Cocktail;
import server.db.entities.CocktailGroup;
import server.db.entities.Cocktail_Ingredient;

import java.util.Collections;
import java.util.List;

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

    public void initialize() {
        super.initialize();
        populateIngredientsDropdown();
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
