package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;

@Entity
@Table(name="apartments")
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="apartmentId")
    private Long id;

    @Column(name="apartmentName", nullable = false)
    private String name;

    @Column(name="apartmentDescription", nullable = false)
    private String description;

    @Column(name="apartmentPrice", nullable = false)
    private double price;

    @Column(name="apartmentCapacity", nullable = false)
    private int capacity;

    @Column(name="apartmentRooms", nullable = false)
    private int rooms;

    @Column(name="apartmentCityName", nullable = false)
    private String cityName;

    public Apartment() { }

    public Apartment(String name, String description, double price, int capacity, int rooms, String cityName) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.capacity = capacity;
        this.rooms = rooms;
        this.cityName = cityName;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
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
                ", cityName='" + cityName + '\'' +
                '}';
    }
}
