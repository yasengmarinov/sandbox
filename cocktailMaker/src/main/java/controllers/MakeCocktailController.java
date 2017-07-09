package controllers;

import controls.CustomControls;
import controllers.interfaces.SimpleController;
import controls.objects.CocktailButton;
import controls.objects.CocktailGroupButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import server.PageNavigator;
import server.db.DAL;
import server.db.entities.Cocktail;
import server.db.entities.CocktailGroup;
import server.session.SessionManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by b06514a on 7/8/2017.
 */
public class MakeCocktailController extends SimpleController{

    @FXML
    public FlowPane cocktailGroup_pane;

    @FXML
    public FlowPane cocktail_pane;

    @FXML
    public Label welcome_label;

    @FXML
    public Button refill_button;

    @FXML
    public Button logOff_button;

    private List<CocktailGroupButton> cocktailGroupButtons = new ArrayList<>();
    private List<CocktailButton> cocktailButtons = new ArrayList<>();

    public void initialize() {

        welcome_label.setText("Welcome " + SessionManager.getSession().getUser().getFirstname());

        addEventHandlers();
        populateCocktailGroupPane();
        addEventHandlers();

    }

    private void populateCocktailGroupPane() {
        List<CocktailGroup> cocktailGroups = DAL.getAll(CocktailGroup.class);
        List<Button> buttons = new ArrayList<>(cocktailGroups.size());

        for (CocktailGroup cocktailGroup : cocktailGroups) {
            CocktailGroupButton cocktailGroupButton = CustomControls.getCocktailGroupButton(cocktailGroup);
            buttons.add(cocktailGroupButton.getButton());
            cocktailGroupButtons.add(cocktailGroupButton);
        }

        cocktailGroup_pane.getChildren().addAll(buttons);
    }

    @Override
    protected void setObjectsVisibility() {

    }

    @Override
    protected void addEventHandlers() {
        logOff_button.addEventHandler(ActionEvent.ACTION, event -> {
            SessionManager.sessionInvalidate();
            PageNavigator.navigateTo(PageNavigator.PAGE_LOGIN);
        });

        for (CocktailGroupButton button : cocktailGroupButtons) {
            button.getButton().addEventHandler(ActionEvent.ACTION, event -> {
                cocktailButtons.clear();
                cocktail_pane.getChildren().clear();
                fillCocktailPane(button.getCocktailGroup());
            });
        }
    }

    private void fillCocktailPane(CocktailGroup cocktailGroup) {
        List<Cocktail> cocktails = DAL.getCocktailsByGroup(cocktailGroup);
        List<Button> buttons = new ArrayList<>(cocktails.size());

        for (Cocktail cocktail : cocktails) {
            CocktailButton cocktailButton = CustomControls.getCocktailButton(cocktail);
            buttons.add(cocktailButton.getButton());
            cocktailButtons.add(cocktailButton);
        }

        cocktail_pane.getChildren().addAll(buttons);
    }

}
