package server.db.objects;

/**
 * Created by B06514A on 6/16/2017.
 */
public class Cocktail_Ingredient {

    private int id;
    private Cocktail cocktail;
    private Ingredient ingredient;
    private int millilitres;

    public Cocktail_Ingredient() {

    }

    public Cocktail_Ingredient(Cocktail cocktail, Ingredient ingredient, int millilitres) {
        this.cocktail = cocktail;
        this.ingredient = ingredient;
        this.millilitres = millilitres;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cocktail getCocktail() {
        return cocktail;
    }

    public void setCocktail(Cocktail cocktail) {
        this.cocktail = cocktail;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public int getMillilitres() {
        return millilitres;
    }

    public void setMillilitres(int millilitres) {
        this.millilitres = millilitres;
    }
}
