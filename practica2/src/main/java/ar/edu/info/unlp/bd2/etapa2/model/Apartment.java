package ar.edu.info.unlp.bd2.etapa2.model;

import java.util.List;

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
                "rooms=" + rooms +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", capacity=" + capacity +
                ", city='" + city + '\'' +
                ", reservations=" + reservations +
                '}';
    }
}
