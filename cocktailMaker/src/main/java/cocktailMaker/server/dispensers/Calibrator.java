package cocktailMaker.server.dispensers;

import org.apache.log4j.Logger;
import cocktailMaker.server.db.entities.Dispenser;
import cocktailMaker.server.db.entities.Ingredient;
import cocktailMaker.server.dispensers.interfaces.DispenserController;

import java.time.Duration;
import java.time.Instant;

/**
 * Created by b06514a on 7/17/2017.
 */
public class Calibrator {
    private static final Logger logger = Logger.getLogger(Calibrator.class.getName());

    protected Ingredient ingredient;
    protected Dispenser dispenser;
    protected Duration duration;

    protected Instant calibrationStart;
    protected DispenserController dispenserController;

    public Calibrator(Ingredient ingredient, Dispenser dispenser) {
        if (ingredient == null || dispenser == null) {
            logger.error("Calibrator could not be created");
        }
        this.ingredient = ingredient;
        this.dispenser = dispenser;
        dispenserController = DispenserControllerManager.getDispenserController(dispenser.getId());
        logger.info("Calibrator created:");
        logger.info("Ingredient: " + ingredient.getId());
        logger.info("Dispenser: " + dispenser.getId());
    }

    public Duration getDuration() {
        return duration;
    }

    public void start() {
        logger.info(String.format("Calibration of %s on Dispenser %s initiated", ingredient.getName(), dispenser.getId()));
        calibrationStart = Instant.now();
        dispenserController.run();
    }

    public void stop() {
        dispenserController.stop();
        duration = java.time.Duration.between(calibrationStart, Instant.now());
        logger.info(String.format("Calibration of %s on Dispenser %s completed in %02d:%03d",
                ingredient.getName(), dispenser.getId(), duration.getSeconds(), duration.getNano() / 1000000));
    }

}
