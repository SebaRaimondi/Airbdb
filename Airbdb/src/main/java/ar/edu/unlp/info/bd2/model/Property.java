package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;

@Entity
@Table(name="properties")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="propertyId")
    protected Long id;

    @Column(nullable=false, unique = true)
    protected String name;

    @Column(nullable=false)
    protected String description;

    @Column(nullable=false)
    protected double price;

    @Column(nullable=false)
    protected int capacity;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="cityId")
    protected City city;

    public Property(){};

    public Property(String name, String description, double price, int capacity, City city) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.capacity = capacity;
        this.city = city;
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
        return city;
    }

    public void setCityId(City city) {
        this.city = city;
    }
}
