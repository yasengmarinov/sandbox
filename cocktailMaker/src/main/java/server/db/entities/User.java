package server.db.entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * Created by B06514A on 6/16/2017.
 */
@Entity
@Table(name = "USERS")
public class User {
    private Integer id;
    private String username;
    private String firstname;
    private String lastname;
    private String password;
    private String magneticCard;
    private String isAdmin;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "USERNAME", unique = true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "FIRSTNAME")
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Basic
    @Column(name = "LASTNAME")
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Basic
    @Column(name = "PASSWORD")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "MAGNETIC_CARD")
    public String getMagneticCard() {
        return magneticCard;
    }

    public void setMagneticCard(String magneticCard) {
        this.magneticCard = magneticCard;
    }

    @Basic
    @Column(name = "IS_ADMIN")
    @Type(type = "char")
    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (firstname != null ? !firstname.equals(user.firstname) : user.firstname != null) return false;
        if (lastname != null ? !lastname.equals(user.lastname) : user.lastname != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (magneticCard != null ? !magneticCard.equals(user.magneticCard) : user.magneticCard != null)
            return false;
        if (isAdmin != null ? !isAdmin.equals(user.isAdmin) : user.isAdmin != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (magneticCard != null ? magneticCard.hashCode() : 0);
        result = 31 * result + (isAdmin != null ? isAdmin.hashCode() : 0);
        return result;
    }
}
