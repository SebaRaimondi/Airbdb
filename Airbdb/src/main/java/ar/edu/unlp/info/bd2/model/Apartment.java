package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;

@Entity
@Table(name="apartments")
public class Apartment extends Property{

    @Column(nullable=false)
    private int rooms;

    public Apartment(String name, String description, double price, int capacity, int rooms, City city) {
        super(name, description, price, capacity, city);
        this.rooms = rooms;
    }

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }
}
