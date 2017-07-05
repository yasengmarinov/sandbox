package controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import server.db.DAL;
import server.db.entities.HistoryLog;

import java.text.SimpleDateFormat;

/**
 * Created by B06514A on 6/22/2017.
 */
public class LogController {

    public static final String DATE_FORMAT = "dd/MM/yy hh:mm";

    @FXML
    public TableView<HistoryLog> log_table;

    @FXML
    public TableColumn<HistoryLog, String> message_column;

    @FXML
    public TableColumn<HistoryLog, String> user_column;

    @FXML
    public TableColumn<HistoryLog, String> date_column;

    private ObservableList<HistoryLog> historyLogObservableList;

    public void initialize() {

        configureTableColumns();
        refreshLogList();
        log_table.setItems(historyLogObservableList);


    }

    private void configureTableColumns() {
        message_column.setCellValueFactory(new PropertyValueFactory<HistoryLog, String>("message"));
        user_column.setCellValueFactory(new PropertyValueFactory<HistoryLog, String>("username"));
        date_column.setCellValueFactory(param -> {
            return new SimpleStringProperty(new SimpleDateFormat(DATE_FORMAT).format(param.getValue().getEventDate()));
        });
    }

    private void refreshLogList() {
        setObservableList();
        FXCollections.sort(historyLogObservableList);
    }

    public void setObservableList() {
        historyLogObservableList = FXCollections.observableArrayList(DAL.getAll(HistoryLog.class));
    }

}
