package server.db.objects;

/**
 * Created by b06514a on 6/10/2017.
 */
public class Ingredient {

    private int id;
    private String name;
    private double velocity;

    public Ingredient() {

    }

    public Ingredient(String name) {
        this.name = name;
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

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }
}
