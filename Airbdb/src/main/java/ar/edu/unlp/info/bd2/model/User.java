package ar.edu.unlp.info.bd2.model;

/* for annotations */
import javax.persistence.*;

@Entity
@Table(name="users")
public class User {
    @Column(name="username",unique=true, nullable=false)
    private String username;

    @Column(name="name", nullable=false)
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="userId")
    private Long id;

    public User() {}

    public User(String username, String name) {
        this.username = username;
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
