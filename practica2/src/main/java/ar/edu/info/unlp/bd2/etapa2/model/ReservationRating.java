package ar.edu.info.unlp.bd2.etapa2.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "ratings")
public class ReservationRating {
    @Id
    private Long id;

    private int points;
    private String comment;

    private Reservation reservation;

    public ReservationRating() {
    }

    public ReservationRating(int points, String comment, Reservation reservation) {
        this.points = points;
        this.comment = comment;
        this.setReservation(reservation);
    }

    public Long getId() {
        return id;
    }

    public int getPoints() {
        return points;
    }

    public String getComment() {
        return comment;
    }

    public Reservation getReservation() {
        return reservation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReservationRating)) return false;
        ReservationRating that = (ReservationRating) o;
        return getPoints() == that.getPoints() &&
                Objects.equals(getId(), that.getId()) &&
                Objects.equals(getComment(), that.getComment()) &&
                Objects.equals(getReservation(), that.getReservation());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getPoints(), getComment(), getReservation());
    }

    @Override
    public String toString() {
        return "ReservationRating{" +
                "id=" + id +
                ", points=" + points +
                ", comment='" + comment + '\'' +
                ", reservation=" + reservation +
                '}';
    }

    private void setReservation(Reservation reservation) {
        this.reservation = reservation;
        reservation.setRating(this);
    }
}
