package server.db.objects;

/**
 * Created by B06514A on 6/16/2017.
 */
public class User {

    private int id;
    private String username;
    private String firstname;
    private String lastname;
    private String password;
    private String magnetic_card;
    private char isAdmin;

    public User() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMagnetic_card() {
        return magnetic_card;
    }

    public void setMagnetic_card(String magnetic_card) {
        this.magnetic_card = magnetic_card;
    }

    public char getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(char isAdmin) {
        this.isAdmin = isAdmin;
    }
}
