package ar.edu.unlp.info.bd2.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class City {
    @Column(name="name", nullable=false)
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cityId")
    private Long id;

    public City(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
