package ar.edu.unlp.info.bd2.services;

import ar.edu.unlp.info.bd2.model.*;
import ar.edu.unlp.info.bd2.repositories.AirBdbRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


public class AirBdbServiceImpl implements AirBdbService {
    private AirBdbRepository repository;

    public AirBdbServiceImpl(AirBdbRepository repository) {
        this.repository = repository;
    }


    @Transactional
    /* creates a new user and returns it. throws UsernameException if the chosen username is already taken */
    public User createUser(String username, String name){
        username = username.toLowerCase();
        /* si uso exception poner en la firma del metodo throws UsernameException en esta clase y en la interfaz */
       /* if (! this.repository.uniqueUsername(username)) throw new UsernameException(); */
        User user = new User (username, name);
        return repository.storeUser(user);
    }


    /* returns an user by a given email, null otherwise */
    public User getUserByUsername(String email) {
        email = email.toLowerCase();
        return repository.getUserByUsername(email);
    }


    @Transactional
    /* creates a new apartment and returns it */
    public Apartment createApartment(String name, String description, double price, int capacity, int rooms, String cityName){
        cityName = cityName.toUpperCase();
        City city = repository.findCityByName(cityName);
        if (city == null){
            city = new City(cityName);
            repository.storeCity(city);
        }
        Apartment apartment = new Apartment(name, description, price, capacity, rooms, city);
        return repository.storeApartment(apartment);
    }


    /* returns a property by a given name, null otherwise */
    public Property getPropertyByName(String name) {
        return repository.getPropertyByName(name);
    }


    @Transactional
    /* creates a new room and returns it */
    public PrivateRoom createRoom(String name, String description, double price, int capacity, int beds, String cityName){
        cityName = cityName.toUpperCase();
        City city = repository.findCityByName(cityName);
        if (city == null){
            city = new City(cityName);
            repository.storeCity(city);
        }
        PrivateRoom room = new PrivateRoom(name, description, price, capacity, beds, city);
        return repository.storeRoom(room);
    }

    @Transactional
    /* creates a new reservation and returns it */
    public Reservation createReservation(long apartmentId, long userId, Date from, Date to) throws ReservationException{
        if (! this.isPropertyAvailable(apartmentId, from, to) ) throw new ReservationException();
        Apartment apartment = (Apartment) repository.getPropertyById(apartmentId);
        User user = repository.getUserById(userId);
        Reservation reservation = new Reservation(apartment, user, from, to);
        return repository.storeReservation(reservation);
    }


    /* returns an user by a given id, null otherwise */
    public User getUserById(Long id) {
        return repository.getUserById(id);
    }

    /* returns an user by a given id, null otherwise */
    public boolean isPropertyAvailable(Long id, Date from, Date to){
        return repository.isPropertyAvailable(id, from, to);
    }

    /* returns an user by a given id, null otherwise */
    public Reservation getReservationById(Long id) {
        return repository.getReservationById(id);
    }

    @Override
    public void cancelReservation(Long reservationId) {
        repository.cancelReservation(reservationId);
    }

    public ReservationRating createRating(Reservation reservation, int points, String comment) {
        ReservationRating rating = new ReservationRating(reservation, points, comment);
        return repository.storeRating(rating);
    }

    @Override
    public void rateReservation(Long reservationId, int points, String comment) throws RateException {
        Reservation reservation = this.getReservationById(reservationId);
        if (reservation.getStatus() != ReservationStatus.FINISHED  ) throw new RateException();

        this.createRating(reservation, points, comment);
    }

    @Override
    public void finishReservation(Long id) {
        repository.finishReservation(id);
    }

    @Override
    public ReservationRating getRatingForReservation(Long reservationId) {
        return repository.getRatingForReservation(reservationId);
    }
}