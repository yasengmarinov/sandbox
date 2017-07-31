package server.events;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import server.db.entities.Cocktail;

/**
 * Created by b06514a on 7/20/2017.
 */
public class CocktailEvent extends Event {

    public static EventType<CocktailEvent> DONE = new EventType<>("DONE");
    public static EventType<CocktailEvent> BEGIN = new EventType<>("BEGIN");
    protected Cocktail cocktail;


    public CocktailEvent(EventType<? extends Event> eventType, Cocktail cocktail) {
        super(eventType);
        this.cocktail = cocktail;
    }

    public CocktailEvent(Object source, EventTarget target, EventType<? extends Event> eventType) {
        super(source, target, eventType);
    }

    public Cocktail getCocktail() {
        return cocktail;
    }
}
