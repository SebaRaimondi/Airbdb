package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;

@Entity
@Table(name="properties")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="propertyId")
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    private String description;

    @Column(nullable=false)
    private double price;

    @Column(nullable=false)
    private int capacity;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn()
    private City cityId;

    public Property(String name, String description, double price, int capacity, City cityId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.capacity = capacity;
        this.cityId = cityId;
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

    public City getCityId() {
        return cityId;
    }

    public void setCityId(City cityId) {
        this.cityId = cityId;
    }
}
