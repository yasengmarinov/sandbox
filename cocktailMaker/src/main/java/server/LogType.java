package server;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by b06514a on 7/3/2017.
 */
public class LogType {

    public static final int TYPE_CREATE_OBJECT = 3;
    public static final int TYPE_REMOVE_OBJECT = 4;
    public static final int TYPE_CREATE_USER = 5;
    public static final int TYPE_UPDATE_USER = 6;
    public static final int TYPE_REMOVE_USER = 7;
    public static final int TYPE_CONFIGURE_DISPENSER = 8;

    public static final int TYPE_MAKE_COCKTAIL = 9;

    private static List<Integer> cocktailEvents;
    static {
        cocktailEvents = new ArrayList<>();
        cocktailEvents.add(TYPE_MAKE_COCKTAIL);
    }

    public static final List<Integer> getCocktailEvents() {
        return cocktailEvents;
    }
}
