package ar.edu.unlp.info.bd2.model;

public class ReservationException extends Exception {
    public ReservationException() { super("Ya existe una reserva en ese rango de fechas !!!"); }
}
