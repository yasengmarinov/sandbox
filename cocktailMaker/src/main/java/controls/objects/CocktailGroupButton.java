package controls.objects;

import javafx.scene.control.Button;
import server.db.entities.CocktailGroup;


/**
 * Created by b06514a on 7/8/2017.
 */
public class CocktailGroupButton {

    CocktailGroup cocktailGroup;
    Button button;

    public CocktailGroupButton(Button button, CocktailGroup cocktailGroup) {
        this.button = button;
        this.button.setText(cocktailGroup.getName());
        this.cocktailGroup = cocktailGroup;
    }

    public Button getButton() {
        return button;
    }

    public CocktailGroup getCocktailGroup() {
        return cocktailGroup;
    }

}
