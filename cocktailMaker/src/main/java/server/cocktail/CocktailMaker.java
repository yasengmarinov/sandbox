package server.cocktail;

import javafx.scene.control.Alert;
import org.apache.log4j.Logger;
import server.Utils;
import server.db.DAL;
import server.db.entities.Cocktail;
import server.db.entities.CocktailIngredient;
import server.db.entities.Dispenser;
import server.db.entities.Ingredient;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by b06514a on 7/18/2017.
 */
public class CocktailMaker {
    private static final Logger logger = Logger.getLogger(CocktailMaker.class.getName());

    private static ExecutorService pool = Executors.newFixedThreadPool(4);

    public static boolean validate(Cocktail cocktail) {
        List<CocktailIngredient> cocktailIngredients = cocktail.getCocktailIngredients();
        Map<Ingredient, Integer> ingredientsAvailability = new HashMap<>();
        for (Dispenser dispenser : DAL.getEnabledDispensers()) {
            if (!ingredientsAvailability.containsKey(dispenser.getIngredient())) {
                ingredientsAvailability.put(dispenser.getIngredient(), dispenser.getMillilitresLeft());
            } else {
                ingredientsAvailability.compute(dispenser.getIngredient(), (ingredient, integer) -> {
                    return Math.max(integer, dispenser.getMillilitresLeft());
                });
            }
        }

        for (CocktailIngredient cocktailIngredient : cocktailIngredients) {
            if (ingredientsAvailability.get(cocktailIngredient.getIngredient()) < cocktailIngredient.getMillilitres()) {
                Utils.Dialogs.openAlert(Alert.AlertType.INFORMATION, Utils.Dialogs.TITLE_INGREDIENTS_UNAVAILABLE,
                        String.format("Not enough %s. Please change the bottle and go to Refill page to update availability",
                                cocktailIngredient.getIngredient().getName()));
                return false;
            }
        }

        return true;
    }

    public static void make(Cocktail cocktail) {

        logger.info("Begin making of cocktail " + cocktail.getName());

        List<Callable<Boolean>> tasks = new LinkedList<>();
        for (CocktailIngredient cocktailIngredient : cocktail.getCocktailIngredients()) {
            tasks.add(new IngredientPourTask(cocktailIngredient));
        }

        try {
            pool.invokeAll(tasks);
        } catch (InterruptedException e) {
            logger.error(e);
        }
    }
}
