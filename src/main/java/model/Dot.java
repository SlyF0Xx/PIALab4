package model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "H_DOTS")
public class Dot implements Serializable{
    @Id()
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DOTS_SEQ")
    @SequenceGenerator(name="DOTS_SEQ", sequenceName="DOTS_SEQ", allocationSize=1)
    Long id;

    @Column(name = "H_X")
    Integer x;

    @Column(name = "H_Y")
    Double y;

    @Column(name = "H_SIZE")
    Integer size;

    @Column(name = "H_SHOOT")
    Boolean shoot;

    @ManyToOne()
    @JoinColumn(name= "H_OWNER")
    User user;

    public Dot() {
    }

    public Dot(Integer x, Double y, Integer size, Boolean shoot, User user) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.shoot = shoot;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Boolean getShoot() {
        return shoot;
    }

    public void setShoot(Boolean shoot) {
        this.shoot = shoot;
    }

    @Override
    public String toString() {
        return "Dot{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", size=" + size +
                ", shoot=" + shoot +
                ", owner=" + user.toString() +
                '}';
    }
}
