package controllers;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.binding.StringBinding;
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
import server.db.entities.Ingredient;
import server.db.entities.Pump;

import java.util.Collections;
import java.util.List;

/**
 * Created by B06514A on 6/18/2017.
 */
public class ConfigurePumpsController {

    @FXML
    public TableView<Pump> pumps_table;

    @FXML
    public TableColumn<Pump, Integer> id_column;

    @FXML
    public TableColumn<Pump, String> ingredients_column;

    @FXML
    public TableColumn<Pump, Boolean> enabled_column;

    @FXML
    public TextField selectedPumpId_field;

    @FXML
    public ComboBox<Ingredient> selectedPumpIngredient_box;

    @FXML
    public CheckBox selectedPumpEnabled_check;

    @FXML
    public Button save_button;

    private ObservableList<Pump> pumpsObservableList = FXCollections.observableArrayList();
    private Property<Pump> selectedPump = new SimpleObjectProperty<>();


    public void initialize() {

        configureTableColumns();

        selectedPump.bind(pumps_table.getSelectionModel().selectedItemProperty());
        pumpsObservableList.addAll(DAL.getAll(Pump.class));
        pumps_table.setItems(pumpsObservableList);
        pumps_table.getSelectionModel().select(0);

        populateSelectedPumpFields();
        populateIngredientsDropdown();
        linkSelectedPumpFieldsToPumpsTable();
        addEventListeners();

    }
    private void addEventListeners() {
        save_button.addEventHandler(ActionEvent.ACTION, event -> {
            Pump pump = pumps_table.getFocusModel().getFocusedItem();

            if (selectedPumpEnabled_check.isSelected() && selectedPumpIngredient_box.getValue() == null) {
                String content = "Please select Ingredient before enabling a Pump";
                Utils.Dialogs.openAlert(Alert.AlertType.INFORMATION, Utils.Dialogs.TITLE_INCONSISTENT_DATA, content);
            } else {
                int focusedPosition = pumps_table.getFocusModel().getFocusedIndex();
                pump.setEnabled(selectedPumpEnabled_check.isSelected());
                pump.setIngredient(selectedPumpIngredient_box.getValue());

                DAL.update(pump);

                pumpsObservableList.set(pumps_table.getFocusModel().getFocusedIndex(), pump);
                FXCollections.sort(pumpsObservableList);

                DAL.addHistoryEntry(LogType.TYPE_CONFIGURE_PUMP,
                        String.format("Pump %d configured with ingredient %s. ", pump.getId(), pump.getIngredient()));

                pumps_table.refresh();
                pumps_table.getFocusModel().focus(focusedPosition);
            }
        });
    }

    private void linkSelectedPumpFieldsToPumpsTable() {

        selectedPump.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                populateSelectedPumpFields();
            }
        });
    }

    private void populateSelectedPumpFields() {
        selectedPumpId_field.setText(selectedPump.getValue().getId().toString());
        selectedPumpIngredient_box.setValue(selectedPump.getValue().getIngredient());
        selectedPumpEnabled_check.setSelected(selectedPump.getValue().getEnabled());
    }

    private void configureTableColumns() {
        id_column.setCellValueFactory(new PropertyValueFactory<Pump, Integer>("id"));
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
        selectedPumpIngredient_box.setItems(FXCollections.observableArrayList(list));
    }

}
