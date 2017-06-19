package controllers;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
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

    public void initialize() {
        configureTableColumns();
        users_table.setItems(usersObservableList);

        refreshUsersList();
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

    private void refreshUsersList() {
        List<User> list = DAO.Users.getUsers();
        Collections.sort(list);
        usersObservableList.clear();
        usersObservableList.addAll(list);

    }

}
