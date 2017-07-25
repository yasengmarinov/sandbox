package controllers;

import controllers.interfaces.SimpleController;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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
import server.db.entities.Dispenser;
import server.db.entities.Ingredient;

import java.util.Collections;
import java.util.List;

/**
 * Created by B06514A on 6/18/2017.
 */
public class ConfigureDispensersController extends SimpleController{

    @FXML
    public TableView<Dispenser> dispensers_table;

    @FXML
    public TableColumn<Dispenser, Integer> id_column;

    @FXML
    public TableColumn<Dispenser, String> ingredients_column;

    @FXML
    public TableColumn<Dispenser, Boolean> enabled_column;

    @FXML
    public TextField selectedDispenserId_field;

    @FXML
    public ComboBox<Ingredient> selectedDispenserIngredient_box;

    @FXML
    public CheckBox selectedDispenserEnabled_check;

    @FXML
    public Button save_button;

    private ObservableList<Dispenser> dispensersObservableList = FXCollections.observableArrayList();
    private Property<Dispenser> selectedDispenser = new SimpleObjectProperty<>();

    @Override
    public void initialize() {

        configureTableColumns();
        setObjectsVisibility();

        selectedDispenser.bind(dispensers_table.getSelectionModel().selectedItemProperty());
        dispensersObservableList.addAll(DAL.getAll(Dispenser.class));
        dispensers_table.setItems(dispensersObservableList);
        dispensers_table.getSelectionModel().select(0);

        populateSelectedDispenserFields();
        populateIngredientsDropdown();

        linkSelectedDispenserFieldsToDispensersTable();
        addEventHandlers();

    }

    @Override
    protected void setObjectsVisibility() {

    }

    @Override
    protected void addEventHandlers() {
        save_button.addEventHandler(ActionEvent.ACTION, event -> {
            Dispenser dispenser = dispensers_table.getFocusModel().getFocusedItem();

            if (selectedDispenserEnabled_check.isSelected() && selectedDispenserIngredient_box.getValue() == null) {
                String content = "Please select Ingredient before enabling a Dispenser";
                Utils.Dialogs.openAlert(Alert.AlertType.INFORMATION, Utils.Dialogs.TITLE_INCONSISTENT_DATA, content);
            } else {
                int focusedPosition = dispensers_table.getFocusModel().getFocusedIndex();
                dispenser.setEnabled(selectedDispenserEnabled_check.isSelected());
                dispenser.setIngredient(selectedDispenserIngredient_box.getValue());
                dispenser.setMillilitresLeft(selectedDispenserIngredient_box.getValue().getSize());

                DAL.update(dispenser);

                dispensersObservableList.set(dispensers_table.getFocusModel().getFocusedIndex(), dispenser);
                FXCollections.sort(dispensersObservableList);

                DAL.addHistoryEntry(LogType.TYPE_CONFIGURE_DISPENSER,
                        String.format("Dispenser %d configured with ingredient %s. ", dispenser.getId(), dispenser.getIngredient()));

                dispensers_table.refresh();
                dispensers_table.getFocusModel().focus(focusedPosition);
            }
        });
    }

    private void linkSelectedDispenserFieldsToDispensersTable() {

        selectedDispenser.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                populateSelectedDispenserFields();
            }
        });
    }

    private void populateSelectedDispenserFields() {
        selectedDispenserId_field.setText(selectedDispenser.getValue().getId().toString());
        selectedDispenserIngredient_box.setValue(selectedDispenser.getValue().getIngredient());
        selectedDispenserEnabled_check.setSelected(selectedDispenser.getValue().getEnabled());
    }

    private void configureTableColumns() {
        id_column.setCellValueFactory(new PropertyValueFactory<Dispenser, Integer>("id"));
        ingredients_column.setCellValueFactory(param -> {
            SimpleStringProperty simpleStringProperty = new SimpleStringProperty(param.getValue().getIngredient() == null ?
                    "Not Configured" : param.getValue().getIngredient().toString());
            return simpleStringProperty;
        });

        enabled_column.setCellValueFactory(param -> {
            SimpleBooleanProperty simpleBooleanProperty = new SimpleBooleanProperty(param.getValue().getEnabled());
            return simpleBooleanProperty;
        });
        enabled_column.setCellFactory(CheckBoxTableCell.forTableColumn(enabled_column));
    }

    private void populateIngredientsDropdown() {
        List<Ingredient> list = DAL.getAll(Ingredient.class);
        Collections.sort(list);
        selectedDispenserIngredient_box.setItems(FXCollections.observableArrayList(list));
    }

}
