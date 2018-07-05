package ar.edu.info.unlp.bd2.etapa2.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
public class Reservation {
    @Id
    private String id;

    private Date from;
    private Date to;
    private double price;
    private ReservationStatus status;

    @DBRef
    private Property property;
    @DBRef
    private User user;
    @DBRef
    private ReservationRating rating;

    public Reservation() {
    }

    public Reservation(Property property, User user, Date from, Date to ) {
        this(property, user, from, to, ReservationStatus.CONFIRMATION_PENDING);
    }

    public Reservation(Property property, User user, Date from, Date to, ReservationStatus initialStatus ) {
        this.from = from;
        this.to = to;
        this.setProperty(property);
        this.setUser(user);
        this.status = initialStatus;
        this.price = this.nigths() * property.getPrice();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public void setPrice(double price) {
        this.price = price;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
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

    public void confirmReservation() {
        this.status = ReservationStatus.CONFIRMED;
    }

    public void finishReservation() {
        this.status = ReservationStatus.FINISHED;
    }

    private int nigths(){
        final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000;
        return (int)((to.getTime() - from.getTime()) / MILLSECS_PER_DAY);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id='" + id + '\'' +
                ", from=" + from +
                ", to=" + to +
                ", price=" + price +
                ", status=" + status +
                ", property=" + property.getId() +
                ", user=" + user.getId() +
                ", rating=" + rating +
                '}';
    }
}
