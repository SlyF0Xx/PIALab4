package model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "H_DOT_USER")
public class User implements Serializable {
    @Id()
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DOT_USER_SEQ")
    @SequenceGenerator(name="DOT_USER_SEQ", sequenceName="DOT_USER_SEQ", allocationSize=1)
    private Long id;

    @Column(name = "H_NAME")
    private String name;

    @Column(name = "H_PASSWORD")
    private int password;

    /*
    @OneToMany(mappedBy = "user")
    private List<Dot> dots;
*/
    public User() {
    }

    public User(String name, int password) {
        this.name = name;
        this.password = password;
        //dots = new LinkedList<>();
    }

    public User(String name, int password, List<Dot> dots) {
        this.name = name;
        this.password = password;
        //this.dots = dots;
    }

    @Override
    public String toString() {
        String tmp = "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", dots=";
        /*for(Dot dot : dots)
        {
            tmp += dot.toString();
        }*/
        return tmp + '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    /*
    public List<Dot> getDots() {
        return dots;
    }

    public void setDots(List<Dot> dots) {
        this.dots = dots;
    }*/
}
