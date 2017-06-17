package server.db.entities;

import javax.persistence.*;

/**
 * Created by B06514A on 6/16/2017.
 */
@Entity
@Table(name = "INGREDIENTS")
public class Ingredient {
    private Integer id;
    private String name;
    private Double velocity;

    public Ingredient() {

    }

    public Ingredient(String name) {
        this.name = name;
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "NAME", unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "VELOCITY")
    public Double getVelocity() {
        return velocity;
    }

    public void setVelocity(Double velocity) {
        this.velocity = velocity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ingredient that = (Ingredient) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (velocity != null ? !velocity.equals(that.velocity) : that.velocity != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (velocity != null ? velocity.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return name;
    }
}
