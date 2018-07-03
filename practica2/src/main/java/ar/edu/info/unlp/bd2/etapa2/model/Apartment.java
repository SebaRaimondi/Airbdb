package ar.edu.info.unlp.bd2.etapa2.model;

import java.util.List;
import java.util.Objects;

public class Apartment extends Property {
    private int rooms;

    public Apartment() {
    }

    public Apartment(String name, String description, double price, int capacity, City city, int rooms) {
        super(name, description, price, capacity, city);
        this.rooms = rooms;
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
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", capacity=" + capacity +
                ", rooms=" + rooms +
                ", city=" + city +
                ", reservations=" + reservations +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Apartment)) return false;
        Apartment apartment = (Apartment) o;
        return getRooms() == apartment.getRooms();
    }

    @Override
    public int hashCode() {

        return Objects.hash(getRooms());
    }
}
