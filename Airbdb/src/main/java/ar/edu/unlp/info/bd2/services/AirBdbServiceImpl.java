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


    /* creates a new apartment and returns it */
    public Apartment createApartment(String name, String description, double price, int capacity, int rooms, String cityName){
       return repository.createApartment(name, description, price, capacity, rooms, cityName);
    }


    /* returns a property by a given name, null otherwise */
    public Property getPropertyByName(String name) {
        return repository.getPropertyByName(name);
    }


    /* creates a new room and returns it */
    public PrivateRoom createRoom(String name, String description, double price, int capacity, int beds, String cityName){
        return repository.createRoom(name, description, price, capacity, beds, cityName);
    }


    /* creates a new reservation and returns it */
    public Reservation createReservation(long apartmentId, long userId, Date from, Date to) throws ReservationException{
        Reservation reservation = null;

        try {
            reservation = repository.createReservation(apartmentId, userId, from, to);
        }
        catch (ReservationException e) {
            e.printStackTrace();
            throw e;
        }

        return reservation;
    }


    /* returns an user by a given id, null otherwise */
    public User getUserById(Long id) {
        return repository.getUserById(id);
    }

    /* returns an user by a given id, null otherwise */
    public boolean isPropertyAvailable(Long id, Date from, Date to){
        return repository.isPropertyAvailable(id, from, to);
    }
}