package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import server.PageNavigator;

/**
 * Created by B06514A on 6/18/2017.
 */
public class MenuController {

    @FXML
    public MenuItem pumps_item;

    @FXML
    public MenuItem ingredients_item;

    @FXML
    public MenuItem users_item;

    public void initialize() {


        pumps_item.addEventHandler(ActionEvent.ACTION, event -> {
            PageNavigator.navigateTo(PageNavigator.PAGE_CONFIGURE_PUMPS);
        });
        ingredients_item.addEventHandler(ActionEvent.ACTION, event -> {
            PageNavigator.navigateTo(PageNavigator.PAGE_CONFIGURE_INGREDIENTS);
        });
        users_item.addEventHandler(ActionEvent.ACTION, event -> {
            PageNavigator.navigateTo(PageNavigator.PAGE_CONFIGURE_USERS);
        });
    }

}
