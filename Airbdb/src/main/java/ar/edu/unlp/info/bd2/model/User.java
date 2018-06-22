package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="users")
public class User {
    @Column(name="username",unique=true, nullable=false)
    private String username;

    @Column(nullable=false)
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="userId")
    private Long id;


    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "user")
    private List<Reservation> reservations = new ArrayList<Reservation>();


    public User() {}

    public User(String username, String name) {
        this.username = username;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getUsername(), user.getUsername()) &&
                Objects.equals(getName(), user.getName()) &&
                Objects.equals(getId(), user.getId()) &&
                reservations.containsAll(user.reservations) && user.reservations.containsAll(reservations);
    }

    @Override
    public int hashCode() {

        return Objects.hash(getUsername(), getName(), getId(), getReservations());
    }

    public void addReservation(Reservation res) {
        this.reservations.add(res);
    }

    public void removeReservation(Reservation res) {
        this.reservations.remove(res);
    }
}
