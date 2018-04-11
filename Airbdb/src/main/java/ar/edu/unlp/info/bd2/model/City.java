package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="cities")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cityId")
    private Long id;

    @Column(name="name", nullable=false)
    private String name;

    @OneToMany(mappedBy = "cityId")
    private List<Property> properties;

    public City(){}

    public City(String name) {
        this.name = name;
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

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }
}
