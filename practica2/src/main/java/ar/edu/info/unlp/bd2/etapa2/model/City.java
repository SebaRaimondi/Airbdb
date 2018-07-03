package ar.edu.info.unlp.bd2.etapa2.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "cities")
public class City {
    @Id
    private Long id;

    private String name;

    private List<Property> properties;

    public City() {
    }

    public City(String name, List<Property> properties) {
        this.name = name;
        this.properties = properties;
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
