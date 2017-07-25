package server.cocktail;

import org.apache.log4j.Logger;
import server.db.DAL;
import server.db.entities.CocktailIngredient;
import server.db.entities.Dispenser;
import server.dispensers.DispenserControllerManager;
import server.dispensers.interfaces.DispenserController;

import java.util.concurrent.Callable;

/**
 * Created by b06514a on 7/18/2017.
 */
public class IngredientPourTask implements Callable<Boolean> {
    private static final Logger logger = Logger.getLogger(IngredientPourTask.class.getName());


    private CocktailIngredient cocktailIngredient;

    public IngredientPourTask(CocktailIngredient cocktailIngredient) {
        this.cocktailIngredient = cocktailIngredient;
    }

    @Override
    public Boolean call() throws Exception {
        Dispenser dispenser = DAL.getDispenserByCocktailIngredient(cocktailIngredient);
        Long msToRun = (long) cocktailIngredient.getIngredient().getVelocity() * cocktailIngredient.getMillilitres() / 100;
        DispenserController dispenserController = DispenserControllerManager.getDispenserController(dispenser);

        logger.info(String.format("Begin pouring %s on Dispenser %d for %d ms", cocktailIngredient.getIngredient().getName(),
                dispenser.getId(), msToRun));
        try {
            dispenserController.run();
            Thread.sleep(msToRun);
            dispenserController.stop();

            dispenser.setMillilitresLeft(dispenser.getMillilitresLeft() - cocktailIngredient.getMillilitres());
            DAL.update(dispenser);
            logger.info(String.format("Availability of %s on dispenser %d reduced to %d", cocktailIngredient.getIngredient(),
                    dispenser.getId(), dispenser.getMillilitresLeft()));
        } catch (InterruptedException e) {
            logger.error(e);
            return false;
        }

        return true;
    }

}