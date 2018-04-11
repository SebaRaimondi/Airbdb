package ar.edu.unlp.info.bd2.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="rooms")
public class PrivateRoom extends Property{

    private int beds;

    public PrivateRoom(String name, String description, double price, int capacity, int beds, City city) {
        super(name, description, price, capacity, city);
        this.beds = beds;
    }

    public int getBeds() {
        return beds;
    }

    public void setBeds(int beds) {
        this.beds = beds;
    }
}
