package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="reservationId")
    private Long id;

    @Column(name = "date_from", nullable = false)
    private Date from;

    @Column(name = "date_to", nullable = false)
    private Date to;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="apartmentId")
    private Apartment apartment;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="userId")
    private User user;

    public Reservation(){}

    public Reservation(Apartment apartment, User user, Date from, Date to ) {
        this.from = from;
        this.to = to;
        this.apartment = apartment;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int nigths(){
        final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000;
        return (int)((to.getTime() - from.getTime()) / MILLSECS_PER_DAY);
    }

    public double getPrice(){
        return this.nigths() * this.getApartment().getPrice();
    }

    public Apartment getProperty(){
        return this.getApartment();
    }


}
