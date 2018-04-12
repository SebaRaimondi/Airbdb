package ar.edu.unlp.info.bd2.model;

public class ReservationException extends Exception {
    public ReservationException() { super("There is an existing reservation in that period of time !!!"); }
}
