package ar.edu.info.unlp.bd2.etapa2.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "reservations")
public class Reservation {
    @Id
    private Long id;

    private Date from;
    private Date to;
    private double price;
    private String status;

    private Property property;
    private User user;
    private ReservationRating rating;

    public Reservation() {
    }

    public Reservation(Property property, User user, Date from, Date to ) {
        this.from = from;
        this.to = to;
        this.setProperty(property);
        this.setUser(user);
        this.status = ReservationStatus.UNCONFIRMED;
        this.price = this.nigths() * property.getPrice();
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

    public double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
        property.addReservation(this);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        user.addReservation(this);
    }

    public ReservationRating getRating() {
        return rating;
    }

    public void setRating(ReservationRating rating) {
        this.rating = rating;
    }

    public void cancelReservation() {
        this.status = ReservationStatus.CANCELED;
    }

    public void finishReservation() {
        this.status = ReservationStatus.FINISHED;
    }

    private int nigths(){
        final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000;
        return (int)((to.getTime() - from.getTime()) / MILLSECS_PER_DAY);
    }
}
