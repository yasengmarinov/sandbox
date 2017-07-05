package controllers;

import controllers.templates.SimpleAddRemovePage;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
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
public class ConfigureIngredientsController extends SimpleAddRemovePage{

    @FXML
    public TableColumn<Ingredient, String> ingredient_column;

    @FXML
    public TableColumn<Ingredient, Boolean> calibrated_column;

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
