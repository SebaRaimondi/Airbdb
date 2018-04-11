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


}
