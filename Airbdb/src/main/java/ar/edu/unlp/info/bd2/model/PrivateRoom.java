package ar.edu.unlp.info.bd2.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="rooms")
public class PrivateRoom {
    @Id
    @Column(name="roomId")
    private Long id;

    @Column(name="roomName")
    private String name;

    public PrivateRoom() { }

    public PrivateRoom(String name) {
        this.name = name;
        this.id = -1L;
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
