package controllers;

import controls.CustomControlsFactory;
import controllers.interfaces.SimpleController;
import controls.objects.CocktailButton;
import controls.objects.CocktailGroupButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import org.apache.log4j.Logger;
import server.PageNavigator;
import server.cocktail.CocktailMaker;
import server.db.DAL;
import server.db.entities.*;
import server.session.SessionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by b06514a on 7/8/2017.
 */
public class MakeCocktailController extends SimpleController{

    private static final Logger logger = Logger.getLogger(MakeCocktailController.class.getName());

    @FXML
    public GridPane main_pane;

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

    private Set<Ingredient> enabledIngredients = DAL.getEnabledDispensers()
            .stream()
            .map(Dispenser::getIngredient)
            .collect(Collectors.toSet());

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
            CocktailGroupButton cocktailGroupButton = CustomControlsFactory.getCocktailGroupButton(cocktailGroup);
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

        refill_button.addEventHandler(ActionEvent.ACTION, event -> {
            PageNavigator.navigateTo(PageNavigator.PAGE_REFILL);
        });

        for (CocktailGroupButton button : cocktailGroupButtons) {
            button.getButton().addEventHandler(ActionEvent.ACTION, event -> {
                cocktailButtons.clear();
                cocktail_pane.getChildren().clear();
                fillCocktailPane(button.getCocktailGroup());
            });
        }

    }

    private void makeCocktail(Cocktail cocktail) {

        if (CocktailMaker.validate(cocktail)) {
            logger.info("Enough availability for making " + cocktail.getName());
            main_pane.setDisable(true);
            CocktailMaker.make(cocktail);
            main_pane.setDisable(false);
        } else {
            logger.info("Not enough availability for making " + cocktail.getName());
        }
    }

    private void fillCocktailPane(CocktailGroup cocktailGroup) {
        List<Cocktail> cocktails = DAL.getCocktailsByGroup(cocktailGroup);
        List<Button> buttons = new ArrayList<>(cocktails.size());

        for (Cocktail cocktail : cocktails) {
            if (isAvailable(cocktail)) {
                CocktailButton cocktailButton = CustomControlsFactory.getCocktailButton(cocktail);
                buttons.add(cocktailButton.getButton());
                cocktailButtons.add(cocktailButton);
            }
        }

        for (CocktailButton button : cocktailButtons) {
            button.getButton().addEventHandler(ActionEvent.ACTION, event -> {
                makeCocktail(button.getCocktail());
            });
        }

        cocktail_pane.getChildren().addAll(buttons);
    }

    private boolean isAvailable(Cocktail cocktail) {
        return enabledIngredients.containsAll(cocktail.getCocktailIngredients().stream().
                map(CocktailIngredient::getIngredient)
                .collect(Collectors.toSet()));
    }

}
