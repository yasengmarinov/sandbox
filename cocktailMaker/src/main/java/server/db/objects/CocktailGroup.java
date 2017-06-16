package server.db.objects;

/**
 * Created by B06514A on 6/16/2017.
 */
public class CocktailGroup {

    private int id;
    private String name;

    public CocktailGroup() {

    }

    public CocktailGroup(String name) {
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
}
