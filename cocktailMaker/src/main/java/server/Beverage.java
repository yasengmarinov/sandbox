package server;

/**
 * Created by b06514a on 6/10/2017.
 */
public class Beverage {
    private int id;
    private String name;

    public Beverage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }
}
