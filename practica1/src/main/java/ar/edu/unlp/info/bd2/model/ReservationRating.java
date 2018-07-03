package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="reservationRatings")
public class ReservationRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ratingId")
    private Long id;

    @OneToOne
    @JoinColumn(name="reservationId")
    private Reservation reservation;

    @Column(name = "points", nullable = false)
    private int points;

    @Column(name = "comment", nullable = false)
    private String comment;

    public ReservationRating(){}

    public ReservationRating(Reservation reservation, int points, String comment) {
        this.setReservation(reservation);
        this.points = points;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public int getPoints() {
        return points;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReservationRating)) return false;
        ReservationRating that = (ReservationRating) o;
        return getPoints() == that.getPoints() &&
                Objects.equals(getId(), that.getId()) &&
                Objects.equals(getReservation(), that.getReservation()) &&
                Objects.equals(getComment(), that.getComment());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getReservation(), getPoints(), getComment());
    }

    @Override
    public String toString() {
        return "ReservationRating{" +
                "id=" + id +
                ", points=" + points +
                ", comment='" + comment + '\'' +
                '}';
    }

    private void setReservation(Reservation reservation) {
        this.reservation = reservation;
        reservation.setRating(this);
    }
}
