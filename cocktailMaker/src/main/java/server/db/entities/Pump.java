package server.db.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by B06514A on 6/18/2017.
 */
@Entity
public class Pumps {
    private Integer id;
    private Integer pinGround;
    private Integer pinIn;
    private String enabled;

    @Id
    @Column(name = "ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "PIN_GROUND")
    public Integer getPinGround() {
        return pinGround;
    }

    public void setPinGround(Integer pinGround) {
        this.pinGround = pinGround;
    }

    @Basic
    @Column(name = "PIN_IN")
    public Integer getPinIn() {
        return pinIn;
    }

    public void setPinIn(Integer pinIn) {
        this.pinIn = pinIn;
    }

    @Basic
    @Column(name = "ENABLED")
    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pumps pumps = (Pumps) o;

        if (id != null ? !id.equals(pumps.id) : pumps.id != null) return false;
        if (pinGround != null ? !pinGround.equals(pumps.pinGround) : pumps.pinGround != null) return false;
        if (pinIn != null ? !pinIn.equals(pumps.pinIn) : pumps.pinIn != null) return false;
        if (enabled != null ? !enabled.equals(pumps.enabled) : pumps.enabled != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (pinGround != null ? pinGround.hashCode() : 0);
        result = 31 * result + (pinIn != null ? pinIn.hashCode() : 0);
        result = 31 * result + (enabled != null ? enabled.hashCode() : 0);
        return result;
    }
}
