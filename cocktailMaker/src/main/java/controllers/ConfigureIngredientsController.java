package controllers;

import controllers.templates.SimpleAddRemovePage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.apache.log4j.Logger;
import server.LogType;
import server.Utils;
import server.db.DAL;
import server.db.entities.Ingredient;
import server.dispensers.Calibrator;

import java.time.Duration;
import java.time.Instant;

/**
 * Created by b06514a on 6/10/2017.
 */
public class ConfigureIngredientsController extends SimpleAddRemovePage {

    public static final int CALIBRATION_INTERVAL_MS = 100;
    public static final int МS_IN_SECOND = 1000;
    public static final int MAX_ALLOWED_CALIBRATION_TIME_SEC = 60;
    public static final int DIVIDE_NANO_TO_GET_3_DIGITS = 1000000;
    public static final int DEFAULT_INGREDIENT_SIZE = 750;
    private static final Logger logger = Logger.getLogger(ConfigureUsersController.class.getName());
    @FXML
    public TableColumn<Ingredient, String> ingredient_column;

    @FXML
    public TableColumn<Ingredient, Boolean> calibrated_column;

    @FXML
    public TableColumn<Ingredient, Integer> size_column;

    @FXML
    public VBox addIngredient_box;

    @FXML
    public VBox calibrate_box;

    @FXML
    public Button calibrate_button;

    @FXML
    public Label calibrateHeader_label;

    @FXML
    public Label timer_label;

    @FXML
    public Button toggleTimer_button;

    @FXML
    public TextField newObjectSize;

    protected Property<Boolean> calibrate_mode = new SimpleBooleanProperty(false);
    protected Property<Boolean> calibrating = new SimpleBooleanProperty(false);
    protected Timeline timeline;
    protected Calibrator calibrator;

    @Override
    protected void configureTableColumns() {
        ingredient_column.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("name"));
        size_column.setCellValueFactory(new PropertyValueFactory<Ingredient, Integer>("size"));
        calibrated_column.setCellValueFactory(param -> {
            SimpleBooleanProperty simpleBooleanProperty = new SimpleBooleanProperty(param.getValue().getVelocity() != null);
            return simpleBooleanProperty;
        });
        calibrated_column.setCellFactory(CheckBoxTableCell.forTableColumn(calibrated_column));
    }

    @Override
    protected boolean addObject() {
        int size = Integer.valueOf(newObjectSize.getText().isEmpty() ? DEFAULT_INGREDIENT_SIZE : Integer.valueOf(newObjectSize.getText()));
        return DAL.persist(new Ingredient(newObjectName.getText(), size));
    }

    @Override
    protected boolean updateObject() {
        int size = Integer.valueOf(newObjectSize.getText().isEmpty() ? DEFAULT_INGREDIENT_SIZE : Integer.valueOf(newObjectSize.getText()));
        Ingredient ingredient = (Ingredient) selectedObject.getValue();
        ingredient.setName(newObjectName.getText());
        ingredient.setSize(size);
        return DAL.update(ingredient);
    }

    @Override
    protected void setClass() {
        this.T = Ingredient.class;
    }

    @Override
    protected void setObjectsVisibility() {
        super.setObjectsVisibility();

        calibrate_box.visibleProperty().bind(calibrate_mode);
        object_table.disableProperty().bind(calibrate_mode);
        addIngredient_box.disableProperty().bind(calibrate_mode);
        calibrate_button.disableProperty().bind(isObjectSelected().not());
        toggleTimer_button.textProperty().bind(new StringBinding() {
            {
                super.bind(calibrating);
            }

            @Override
            protected String computeValue() {
                return calibrating.getValue() ? "Stop" : "Start";
            }
        });
    }

    @Override
    protected void addEventHandlers() {
        super.addEventHandlers();
        calibrate_button.addEventHandler(ActionEvent.ACTION, event -> {
            if (DAL.getDispenserByIngredient((Ingredient) selectedObject.getValue()) == null) {
                Utils.Dialogs.openAlert(Alert.AlertType.INFORMATION, Utils.Dialogs.TITLE_INCONSISTENT_DATA, Utils.Dialogs.CONTENT_ADD_INGREDIENT_TO_DISPENSER);
            } else {
                openCalibrationPane();
            }
        });

        toggleTimer_button.addEventHandler(ActionEvent.ACTION, event -> {
            if (calibrating.getValue()) {
                calibrationStop();
            } else {
                calibrationStart();
            }
        });
    }

    @Override
    protected void clearInputFields() {
        super.clearInputFields();
        newObjectSize.clear();
    }

    @Override
    protected void fillInputFields() {
        super.fillInputFields();
        newObjectSize.setText(((Ingredient) selectedObject.getValue()).getSize().toString());
    }

    private void calibrationStart() {
        calibrating.setValue(true);

        calibrator = new Calibrator((Ingredient) selectedObject.getValue(), DAL.getDispenserByIngredient((Ingredient) selectedObject.getValue()));

        timeline = new Timeline();
        timeline.setCycleCount(MAX_ALLOWED_CALIBRATION_TIME_SEC * МS_IN_SECOND / CALIBRATION_INTERVAL_MS);

        Instant start = Instant.now();
        timeline.getKeyFrames().add(new KeyFrame(javafx.util.Duration.millis(CALIBRATION_INTERVAL_MS), event -> {
            Duration duration = java.time.Duration.between(start, Instant.now());
            timeLabelSetText(duration);
        }));

        timeline.playFromStart();
        calibrator.start();
    }

    private void calibrationStop() {
        calibrator.stop();
        Duration duration = calibrator.getDuration();
        calibrating.setValue(false);
        timeline.stop();
        timeLabelSetText(duration);

        if (isDurationValid(duration)) {
            Ingredient ingredient = (Ingredient) selectedObject.getValue();
            ingredient.setVelocity((int) duration.getSeconds() * 1000 + duration.getNano() / DIVIDE_NANO_TO_GET_3_DIGITS);
            DAL.update(ingredient);
            DAL.addHistoryEntry(LogType.TYPE_UPDATE_OBJECT, String.format("Velocity of Ingredient %s set", ingredient.getName()));
            Utils.Dialogs.openAlert(Alert.AlertType.INFORMATION, Utils.Dialogs.TITLE_CALIBRATION, Utils.Dialogs.CONTENT_CALIBRATION_SUCCESS);
            refreshObjectList();
        } else {
            Utils.Dialogs.openAlert(Alert.AlertType.ERROR, Utils.Dialogs.TITLE_CALIBRATION, Utils.Dialogs.CONTENT_CALIBRATION_FAILED);
        }
        calibrate_mode.setValue(false);
    }

    private void timeLabelSetText(Duration duration) {
        timer_label.setText(String.format("%02d:%03d", duration.getSeconds(), duration.getNano() / DIVIDE_NANO_TO_GET_3_DIGITS));
    }

    private void openCalibrationPane() {
        Utils.Dialogs.openAlert(Alert.AlertType.INFORMATION, Utils.Dialogs.TITLE_CALIBRATE_INSTRUCTIONS, Utils.Dialogs.CONTENT_CALIBRATE_INSTRUCTIONS);
        calibrate_mode.setValue(true);

        calibrateHeader_label.setText(String.format("Calibrating %s on Dispenser %d", selectedObject.getValue(),
                DAL.getDispenserByIngredient((Ingredient) selectedObject.getValue()).getId()));

    }


    private boolean isDurationValid(Duration duration) {
        boolean isValid = true;
        if (duration == null)
            isValid = false;
        if (duration.getSeconds() > MAX_ALLOWED_CALIBRATION_TIME_SEC)
            isValid = false;

        return isValid;
    }

}
