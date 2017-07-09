package controllers;

import controllers.templates.SimpleAddRemovePage;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import server.Utils;
import server.db.DAL;
import server.db.entities.Ingredient;

import static server.Utils.Dialogs.openAlert;

/**
 * Created by b06514a on 6/10/2017.
 */
public class ConfigureIngredientsController extends SimpleAddRemovePage{

    @FXML
    public TableColumn<Ingredient, String> ingredient_column;

    @FXML
    public TableColumn<Ingredient, Boolean> calibrated_column;

    @FXML
    public Button calibrate_button;

    @Override
    public void initialize() {
        super.initialize();
        configureCalibrateButton();
    }

    private void configureCalibrateButton() {
        calibrate_button.disableProperty().bind(isObjectSelected().not());

        calibrate_button.addEventHandler(ActionEvent.ACTION, event -> {
            if (DAL.getDispenserByIngredient((Ingredient) selectedObject.getValue()) == null) {
                Utils.Dialogs.openAlert(Alert.AlertType.INFORMATION, Utils.Dialogs.TITLE_INCONSISTENT_DATA, Utils.Dialogs.CONTENT_ADD_INGREDIENT_TO_DISPENSER);
            }
        });
    }

    @Override
    protected void configureTableColumns() {
        ingredient_column.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("name"));
        calibrated_column.setCellValueFactory(param -> {
            SimpleBooleanProperty simpleBooleanProperty = new SimpleBooleanProperty(param.getValue().getVelocity() != null);
            return simpleBooleanProperty;
        });
        calibrated_column.setCellFactory(CheckBoxTableCell.forTableColumn(calibrated_column));
    }

    @Override
    protected boolean addObject() {
        return DAL.persist(new Ingredient(newObjectName.getText()));
    }

    @Override
    protected void setClass() {
        this.T = Ingredient.class;
    }

}
