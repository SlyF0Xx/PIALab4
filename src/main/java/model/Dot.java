package model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "H_DOTS")
public class Dot implements Serializable{
    @Id()
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DOTS_SEQ")
    @SequenceGenerator(name="DOTS_SEQ", sequenceName="dots_id_seq", allocationSize=1)
    //DOTS_SEQ
    Long id;

    @Column(name = "H_X")
    Double x;

    @Column(name = "H_Y")
    Double y;

    @Column(name = "H_SIZE")
    Double radius;

    @Column(name = "H_SHOOT")
    Boolean isCovered;

    @ManyToOne()
    @JoinColumn(name= "H_OWNER")
    @JsonIgnore
    User user;

    public Dot() {
    }

    public Dot(Double x, Double y, Double radius, Boolean isCovered, User user) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.isCovered = isCovered;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getRadius() {
        return radius;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }

    public Boolean getIsCovered() {
        return isCovered;
    }

    public void setIsCovered(Boolean isCovered) {
        this.isCovered = isCovered;
    }

    @Override
    public String toString() {
        return "Dot{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", size=" + radius +
                ", shoot=" + isCovered +
                ", owner=" + user.toString() +
                '}';
    }
}
