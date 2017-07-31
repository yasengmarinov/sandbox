package ui.controllers;

import javafx.collections.FXCollections;
import server.db.DAO;

/**
 * Created by b06514a on 7/8/2017.
 */
public class CocktailLogController extends LogController {

    @Override
    protected void setObservableList() {
        historyLogObservableList = FXCollections.observableArrayList(DAO.getCocktailLog());
    }

}
