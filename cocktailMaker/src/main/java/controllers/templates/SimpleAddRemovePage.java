package controllers.templates;

import controllers.interfaces.SimpleController;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import server.LogType;
import server.Utils;
import server.db.DAL;
import server.db.entities.interfaces.NamedEntity;

import java.util.Collections;
import java.util.List;

import static server.Utils.Dialogs.openAlert;

/**
 * Created by b06514a on 6/10/2017.
 */
public abstract class SimpleAddRemovePage<T extends NamedEntity & Comparable<T>> extends SimpleController {

    @FXML
    public TableView<T> object_table;

    @FXML
    public TextField newObjectName;

    @FXML
    public Button add_button;

    @FXML
    public Button remove_button;

    @FXML
    public Button edit_button;

    @FXML
    public Button cancel_button;

    protected Class T;
    protected Property<T> selectedProperty = new SimpleObjectProperty<>();

    ObservableList<T> observableList = FXCollections.observableArrayList();
    SimpleBooleanProperty editMode = new SimpleBooleanProperty(false);

    public void initialize() {
        setClass();
        selectedProperty.bind(object_table.getSelectionModel().selectedItemProperty());
        configureTableColumns();
        setTableObjectAndFocus();
        setObjectsVisibility();
        addEventHandlers();
    }

    protected void setTableObjectAndFocus() {
        object_table.setItems(observableList);
        refreshObjectList();
        if (object_table.getItems().size() != 0)
            object_table.getSelectionModel().select(0);
    }

    protected abstract void configureTableColumns();

    @Override
    protected void setObjectsVisibility() {
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
        object_table.disableProperty().bind(editMode);

        BooleanBinding removeButtonEnabled = isObjectSelected();
        remove_button.disableProperty().bind(removeButtonEnabled.not());

        add_button.disableProperty().bind(isReadyForAdd().not());

        edit_button.disableProperty().bind(removeButtonEnabled.not());

    }

    protected BooleanBinding isObjectSelected() {
        return Bindings.and(newObjectName.focusedProperty().not(), object_table.getFocusModel().focusedItemProperty().isNotNull());
    }

    @Override
    protected void addEventHandlers() {
        add_button.addEventHandler(ActionEvent.ACTION, event -> {
            boolean success;
            if (editMode.getValue()) {
                success = updateObject();
            } else {
                success = addObject();
            }
            if (success) {
                DAL.addHistoryEntry(LogType.TYPE_CREATE_OBJECT, "New " + T.getSimpleName() + " created: " + newObjectName.getText());
                clearInputFields();
                refreshObjectList();
                editMode.set(false);
            } else {
                openAlert(Alert.AlertType.INFORMATION, Utils.Dialogs.TITLE_INCONSISTENT_DATA, "Please make sure the name is unique!");
            }
        });

        remove_button.addEventHandler(ActionEvent.ACTION, event -> {
            if (DAL.delete(object_table.getFocusModel().getFocusedItem())) {
                DAL.addHistoryEntry(LogType.TYPE_REMOVE_OBJECT,
                        T.getSimpleName() + " deleted: " + object_table.getFocusModel().getFocusedItem());
                refreshObjectList();
            } else {
                openAlert(Alert.AlertType.WARNING, Utils.Dialogs.TITLE_DELETE_FAILED,
                        "Please make sure the " + T.getSimpleName() + " is not used");
            }
        });

        edit_button.addEventHandler(ActionEvent.ACTION, event -> {
            newObjectName.setText(object_table.getFocusModel().getFocusedItem().getName());
            editMode.set(true);
        });

        cancel_button.addEventHandler(ActionEvent.ACTION, event -> {
            clearInputFields();
            editMode.set(false);
        });
    }

    protected void clearInputFields() {
        newObjectName.clear();
    }

    protected abstract boolean addObject();

    protected boolean updateObject() {
        boolean success;
        T object = object_table.getFocusModel().getFocusedItem();
        object.setName(newObjectName.getText());
        success = DAL.update(object);
        return success;
    }

    protected BooleanBinding isReadyForAdd() {
        return Bindings.isNotEmpty(newObjectName.textProperty());
    }

    protected void refreshObjectList() {
        List<T> list = DAL.getAll(T);
        Collections.sort(list);
        observableList.clear();
        observableList.addAll(list);
    }

    protected abstract void setClass();

}
