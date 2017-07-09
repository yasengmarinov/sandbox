package controls;

import controls.objects.CocktailButton;
import controls.objects.CocktailGroupButton;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import server.ServerLauncher;
import server.db.entities.Cocktail;
import server.db.entities.CocktailGroup;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by b06514a on 7/8/2017.
 */
public class CustomControls {

    private static final Logger logger = Logger.getLogger(CustomControls.class.getName());


    public static CocktailGroupButton getCocktailGroupButton(CocktailGroup cocktailGroup) {
        String cocktailGroupButtonLocation = "views/elements/cocktailGroup_button.fxml";
        try {
            Button button = FXMLLoader.load(CustomControls.class.getClassLoader().getResource(cocktailGroupButtonLocation));
            CocktailGroupButton cocktailGroupButton = new CocktailGroupButton(button, cocktailGroup);
            return cocktailGroupButton;
        } catch (IOException e) {
            logger.info(e.toString());
        }
        return null;
    }

    public static CocktailButton getCocktailButton(Cocktail cocktail) {
        String cocktailButtonLocation = "views/elements/cocktail_button.fxml";
        try {
            Button button = FXMLLoader.load(CustomControls.class.getClassLoader().getResource(cocktailButtonLocation));
            CocktailButton cocktailButton = new CocktailButton(button, cocktail);
            return cocktailButton;
        } catch (IOException e) {
            logger.info(e.toString());
        }
        return null;
    }

}
