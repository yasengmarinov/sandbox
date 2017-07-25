package controllers;

import controllers.interfaces.SimpleController;
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
import server.db.entities.HistoryLog;
import server.db.entities.User;

import java.util.Collections;
import java.util.List;

/**
 * Created by B06514A on 6/19/2017.
 */
public class ConfigureUsersController extends SimpleController {

    @FXML
    public TableView<User> users_table;

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
    public PasswordField confirmPassword_field;

    @FXML
    public CheckBox admin_checkbox;

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

    @Override
    public void initialize() {
        configureTableColumns();
        users_table.setItems(usersObservableList);

        refreshUsersList();

        setObjectsVisibility();
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

    @Override
    protected void setObjectsVisibility() {

        //Add button
        add_button.textProperty().bind(new StringBinding() {
            {
                super.bind(editMode);
            }

            @Override
            protected String computeValue() {
                return (editMode.getValue() ? "Save" : "Add");
            }
        });
        BooleanBinding addButtonEnabled = Bindings.and
                (username_field.textProperty().isNotEmpty(), confirmPassword_field.textProperty().isNotEmpty()).and(password_field.textProperty().isNotEmpty()).
                or(editMode);
        add_button.disableProperty().bind(addButtonEnabled.not());

        //Remove and Edit buttons
        BooleanBinding removeEditButtonsEnabled = Bindings.and(users_table.focusedProperty(), users_table.getFocusModel().focusedItemProperty().isNotNull()).and(editMode.not());
        remove_button.disableProperty().bind(removeEditButtonsEnabled.not().
                or(isDefaultAdminSelected()));

        //Edit button
        edit_button.disableProperty().bind(removeEditButtonsEnabled.not());

        //Cancel button
        cancel_button.visibleProperty().bind(editMode);

        //Set Card button
        setCard_button.disableProperty().bind(removeEditButtonsEnabled.not());

        //Username field
        username_field.disableProperty().bind(editMode);

        //Confirm password field
        confirmPassword_field.disableProperty().bind(password_field.textProperty().isEmpty());

        //Admin checkbox
        admin_checkbox.disableProperty().bind(editMode.and(isDefaultAdminSelected()));
    }

    private BooleanBinding isDefaultAdminSelected() {
        return users_table.getSelectionModel().selectedItemProperty().isEqualTo(DAL.getUser("admin"));
    }

    @Override
    protected void addEventHandlers() {
        add_button.addEventHandler(ActionEvent.ACTION, event -> {
            if (!passwordsMatch()) {
                Utils.Dialogs.openAlert(Alert.AlertType.WARNING, Utils.Dialogs.TITLE_PASSWORD_DO_NOT_MATCH, Utils.Dialogs.CONTENT_PASSWORDS_DO_NOT_MATCH);
                password_field.clear();
                confirmPassword_field.clear();
                return;
            }
            boolean success;
            if (editMode.getValue()) {
                success = updateUser();
            } else {
                success = createUser();
            }

            if (success) {
                clearFields();
                editMode.setValue(false);
                refreshUsersList();
            } else {
                Utils.Dialogs.openAlert(Alert.AlertType.INFORMATION, Utils.Dialogs.TITLE_INCONSISTENT_DATA,
                        "Please make sure the UserID is unique");
            }
        });

        remove_button.addEventHandler(ActionEvent.ACTION, event -> {
            if (DAL.delete(users_table.getFocusModel().getFocusedItem())) {
                refreshUsersList();
            } else {
                Utils.Dialogs.openAlert(Alert.AlertType.WARNING, Utils.Dialogs.TITLE_DELETE_FAILED, "Delete of user failed");
            }
        });

        edit_button.addEventHandler(ActionEvent.ACTION, event -> {
            populateFields();
            editMode.setValue(true);
        });

        cancel_button.addEventHandler(ActionEvent.ACTION, event -> {
            editMode.setValue(false);
            clearFields();
        });
    }

    private boolean createUser() {
        boolean success;
        success = DAL.persist(new User(username_field.getText().toLowerCase(), firstname_field.getText(),
                lastname_field.getText(), Utils.md5(password_field.getText()), admin_checkbox.isSelected()));
        if (success)
            DAL.persist(new HistoryLog(LogType.TYPE_CREATE_OBJECT,
                    "New user created: " + username_field.getText()));
        return success;
    }

    private boolean updateUser() {
        boolean success;

        User user = users_table.getFocusModel().getFocusedItem();
        user.setFirstname(firstname_field.getText());
        user.setLastname(lastname_field.getText());
        user.setIsAdmin(admin_checkbox.isSelected());
        if (!password_field.getText().isEmpty()) {
            user.setPassword(Utils.md5(password_field.getText()));
        }

        success = DAL.update(user);

        if (success)
            DAL.persist(new HistoryLog(LogType.TYPE_UPDATE_OBJECT, "User updated: " + user.getUsername()));
        return success;
    }

    private void populateFields() {
        User user = users_table.getFocusModel().getFocusedItem();
        username_field.setText(user.getUsername());
        firstname_field.setText(user.getFirstname());
        lastname_field.setText(user.getLastname());
        admin_checkbox.setSelected(user.getIsAdmin());
    }

    private boolean passwordsMatch() {
        return password_field.getText().equals(confirmPassword_field.getText());
    }

    private void clearFields() {
        firstname_field.clear();
        lastname_field.clear();
        password_field.clear();
        confirmPassword_field.clear();
        username_field.clear();
        admin_checkbox.setSelected(false);
    }

    private void refreshUsersList() {
        List<User> list = DAL.getAll(User.class);
        Collections.sort(list);
        usersObservableList.clear();
        usersObservableList.addAll(list);

    }

}
