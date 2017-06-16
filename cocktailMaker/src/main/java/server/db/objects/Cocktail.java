package server.db.objects;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by B06514A on 6/16/2017.
 */

public class Cocktail {

    private int id;
    private String name;
    private CocktailGroup cocktailGroup;
    private Set<Cocktail_Ingredient> ingredients;

    public Cocktail() {
        ingredients = new HashSet<>();
    }

    public Cocktail(String name, CocktailGroup cocktailGroup) {
        this.name = name;
        this.cocktailGroup = cocktailGroup;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CocktailGroup getCocktailGroup() {
        return cocktailGroup;
    }

    public void setCocktailGroup(CocktailGroup cocktailGroup) {
        this.cocktailGroup = cocktailGroup;
    }

    public Set<Cocktail_Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Cocktail_Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
