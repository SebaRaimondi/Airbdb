package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Apartment extends Property{

    @Column
    private int rooms;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "apartment")
    private List<Reservation> reservations;

    public Apartment(){}

    public Apartment(String name, String description, double price, int capacity, int rooms, City city) {
        super(name, description, price, capacity, city);
        this.rooms = rooms;
        this.reservations = new ArrayList();
    }

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    @Override
    public String toString() {
        return "Apartment{" +
                "rooms=" + rooms +
                ", reservations=" + reservations +
                ", id=" + this.getId() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Apartment apartment = (Apartment) o;
        return getRooms() == apartment.getRooms() && getId() == apartment.getId() &&
                reservations.containsAll(apartment.reservations) && apartment.reservations.containsAll(reservations);
    }

    @Override
    public int hashCode() {

        return Objects.hash(getRooms(), reservations);
    }
}
