package controllers;

import controllers.interfaces.SimpleController;
import controls.CustomControlsFactory;
import controls.objects.CocktailButton;
import controls.objects.CocktailGroupButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.apache.log4j.Logger;
import server.Events.CocktailEvent;
import server.LogType;
import server.PageNavigator;
import server.cocktail.CocktailMaker;
import server.db.DAL;
import server.db.entities.*;
import server.session.SessionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Created by b06514a on 7/8/2017.
 */
public class MakeCocktailController extends SimpleController{

    private static final Logger logger = Logger.getLogger(MakeCocktailController.class.getName());

    @FXML
    public GridPane main_pane;

    @FXML
    public Pane makingCocktail_pane;

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
        setObjectsVisibility();
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
        makingCocktail_pane.setVisible(false);
    }

    @Override
    protected void addEventHandlers() {
        main_pane.addEventFilter(CocktailEvent.DONE, event -> {
            makingCocktail_pane.setVisible(false);
            main_pane.setDisable(false);
            DAL.addHistoryEntry(LogType.TYPE_MAKE_COCKTAIL, String.format("Cocktail %s made", event.getCocktail().getName()));
        });

        main_pane.addEventFilter(CocktailEvent.BEGIN, event -> {
            main_pane.setDisable(true);
            makingCocktail_pane.setVisible(true);
        });

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
            logger.info("Begin making " + cocktail.getName());
            main_pane.fireEvent(new CocktailEvent(CocktailEvent.BEGIN, cocktail));
            runCocktailTask(cocktail);
        } else {
            logger.info("Not enough availability for making " + cocktail.getName());
        }
    }

    private void runCocktailTask(Cocktail cocktail) {
        ExecutorService es = Executors.newSingleThreadExecutor();
        es.submit(() -> {
            CocktailMaker.make(cocktail);
            main_pane.fireEvent(new CocktailEvent(CocktailEvent.DONE, cocktail));
        });
    }

    private Dialog<Boolean> getMakingCocktailDialog() {
        Dialog<Boolean> dialog = new Dialog<>();
        dialog.setTitle("Please wait");
        dialog.setContentText("Making a cocktail is in progress...");

        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.getDialogPane().lookupButton(ButtonType.CLOSE).setVisible(false);
        return dialog;
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
