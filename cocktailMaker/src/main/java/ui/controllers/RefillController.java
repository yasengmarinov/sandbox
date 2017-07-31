package ui.controllers;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import server.PageNavigator;
import server.db.DAO;
import server.db.entities.Dispenser;
import server.db.entities.Ingredient;
import server.session.SessionManager;


/**
 * Created by b06514a on 7/18/2017.
 */
public class RefillController {

    @FXML
    public TableView<Dispenser> dispenser_table;

    @FXML
    public TableColumn<Dispenser, Integer> dispenserId_column;

    @FXML
    public TableColumn<Dispenser, Ingredient> ingredient_column;

    @FXML
    public TableColumn<Dispenser, String> available_column;

    @FXML
    public Button logOff_button;

    @FXML
    public Button back_button;

    @FXML
    public Button refill_button;

    ObservableList<Dispenser> dispenserObservableList = FXCollections.observableArrayList();
    Property<Dispenser> selectedProperty = new SimpleObjectProperty<>();

    public void initialize() {
        configureTableColumns();
        refreshDispenserList();
        dispenser_table.setItems(dispenserObservableList);
        dispenser_table.getSelectionModel().selectFirst();
        selectedProperty.bind(dispenser_table.getSelectionModel().selectedItemProperty());
        addEventHandlers();
    }

    private void addEventHandlers() {
        logOff_button.addEventHandler(ActionEvent.ACTION, event -> {
            SessionManager.sessionInvalidate();
            PageNavigator.navigateTo(PageNavigator.PAGE_LOGIN);
        });

        back_button.addEventHandler(ActionEvent.ACTION, event -> {
            PageNavigator.navigateTo(PageNavigator.PAGE_MAKE_COCKTAIL);
        });

        refill_button.addEventHandler(ActionEvent.ACTION, event -> {
            Dispenser dispenser = selectedProperty.getValue();
            dispenser.setMillilitresLeft(dispenser.getIngredient().getSize());
            DAO.update(dispenser);
            refreshDispenserList();
        });
    }

    private void refreshDispenserList() {
        dispenserObservableList.clear();
        dispenserObservableList.addAll(DAO.getEnabledDispensers());
        dispenser_table.refresh();
    }

    protected void configureTableColumns() {
        dispenserId_column.setCellValueFactory(new PropertyValueFactory<Dispenser, Integer>("id"));
        ingredient_column.setCellValueFactory(new PropertyValueFactory<Dispenser, Ingredient>("ingredient"));
        available_column.setCellValueFactory(param -> {
            SimpleStringProperty stringProperty = new SimpleStringProperty(param.getValue().getMillilitresLeft() + " ml");
            return stringProperty;
        });
    }

}
