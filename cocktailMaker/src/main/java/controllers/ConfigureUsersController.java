package controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import server.Utils;
import server.db.DAO;
import server.db.entities.User;

import java.util.Collections;
import java.util.List;

/**
 * Created by B06514A on 6/19/2017.
 */
public class ConfigureUsersController {

    @FXML
    public TableView users_table;

    @FXML
    public TableColumn<User, String> username_column;

    @FXML
    public TableColumn<User, String> firstname_column;

    @FXML
    public TableColumn<User, String> lastname_column;

    @FXML
    public TableColumn<User, Boolean> card_column;

    @FXML
    public TableColumn<User, Boolean> admin_column;

    @FXML
    public TextField username_field;

    @FXML
    public TextField firstname_field;

    @FXML
    public TextField lastname_field;

    @FXML
    public PasswordField password_field;

    @FXML
    public CheckBox admin_checkbox;

    @FXML
    public CheckBox card_checkbox;

    @FXML
    public Button setCard_button;

    @FXML
    public Button cancel_button;

    @FXML
    public Button add_button;

    @FXML
    public Button remove_button;

    @FXML
    public Button edit_button;

    private ObservableList<User> usersObservableList = FXCollections.observableArrayList();
    private SimpleBooleanProperty editMode = new SimpleBooleanProperty(false);

    public void initialize() {
        configureTableColumns();
        users_table.setItems(usersObservableList);

        refreshUsersList();
        
        setButtonsVisibility();
        addEventHandlers();
    }

    private void configureTableColumns() {
        username_column.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        firstname_column.setCellValueFactory(new PropertyValueFactory<User, String>("firstname"));
        lastname_column.setCellValueFactory(new PropertyValueFactory<User, String>("lastname"));

        card_column.setCellValueFactory(param -> {
            SimpleBooleanProperty simpleBooleanProperty = new SimpleBooleanProperty(param.getValue().getMagneticCard() != null);
            return simpleBooleanProperty;
        });
        card_column.setCellFactory(CheckBoxTableCell.forTableColumn(card_column));

        admin_column.setCellValueFactory(param -> {
            SimpleBooleanProperty simpleBooleanProperty = new SimpleBooleanProperty(param.getValue().getIsAdmin());
            return simpleBooleanProperty;
        });
        admin_column.setCellFactory(CheckBoxTableCell.forTableColumn(admin_column));
    }

    private void setButtonsVisibility() {
        BooleanBinding removeEditButtonsEnabled = Bindings.and(users_table.focusedProperty(), users_table.getFocusModel().focusedItemProperty().isNotNull());
        remove_button.disableProperty().bind(removeEditButtonsEnabled.not());
        edit_button.disableProperty().bind(removeEditButtonsEnabled.not());

        BooleanBinding addButtonEnabled = Bindings.and(username_field.textProperty().isNotEmpty(), password_field.textProperty().isNotEmpty());
        add_button.disableProperty().bind(addButtonEnabled.not());

        cancel_button.visibleProperty().bind(editMode);
    }

    private void addEventHandlers() {
        add_button.addEventHandler(ActionEvent.ACTION, event -> {
            boolean success;
            if (editMode.getValue()) {
                User user = (User) users_table.getFocusModel().getFocusedItem();
                user.setFirstname(firstname_field.getText());
                user.setLastname(lastname_field.getText());
                user.setIsAdmin(admin_checkbox.isSelected());
                user.setPassword(Utils.generateMd5(password_field.getText()));

                success = DAO.Users.updateUser(user);
            } else {
                success = DAO.Users.addUser(new User(username_field.getText(), firstname_field.getText(),
                        lastname_field.getText(), Utils.generateMd5(password_field.getText()), admin_checkbox.isSelected()));
            }

            if (success) {
                clearFields();
                refreshUsersList();
            } else {
                Utils.Dialogs.openAlert(Alert.AlertType.INFORMATION, Utils.Dialogs.TITLE_INCONSISTENT_DATA,
                        "Please make sure the UserID is unique");
            }
        });
    }

    private void clearFields() {
        firstname_field.clear();
        lastname_field.clear();
        password_field.clear();
        username_field.clear();
        admin_checkbox.setSelected(false);
        card_checkbox.setSelected(false);
    }

    private void refreshUsersList() {
        List<User> list = DAO.Users.getUsers();
        Collections.sort(list);
        usersObservableList.clear();
        usersObservableList.addAll(list);

    }

}
