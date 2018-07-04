package ar.edu.info.unlp.bd2.etapa2.service.impl;

import ar.edu.info.unlp.bd2.etapa2.repository.AirBdbRepository;
import ar.edu.info.unlp.bd2.etapa2.service.AirBdbService;
import ar.edu.info.unlp.bd2.etapa2.model.*;
import ar.edu.info.unlp.bd2.etapa2.model.Reservation.ReservationStatus;
import ar.edu.info.unlp.bd2.etapa2.exceptions.*;
import ar.edu.info.unlp.bd2.etapa2.utils.ReservationCount;

import java.util.Date;
import java.util.List;

public class AirBdbServiceImpl implements AirBdbService {
    private AirBdbRepository repository;

    public void clearDb() {
        repository.clearDb();
    }

    @Override
    public Reservation createReservation(String propertyId, String userId, Date from, Date to, ReservationStatus initialStatus) throws ReservationException {
        if (!repository.isPropertyAvailable(propertyId, from, to) ) throw new ReservationException();
        Property property = repository.getPropertyById(propertyId);
        User user = repository.getUserById(userId);
        Reservation reservation = new Reservation(property, user, from, to, initialStatus);
        System.out.println("!--------------------- property");
        reservation = repository.insert(reservation);
        repository.save(user);
        repository.save(property);
        return reservation;
    }

    @Override
    public User getUserById(String id) {
        return repository.getUserById(id);
    }

    public AirBdbRepository getRepository() {
        return repository;
    }

    public void setRepository(AirBdbRepository repository) {
        this.repository = repository;
    }

    public AirBdbServiceImpl(AirBdbRepository repository) {
        this.repository = repository;
    }

    /* creates a new user and returns it. Throws RepeatedUsernameException if the chosen username is already taken */
    public User createUser(String username, String name) throws RepeatedUsernameException {
        if (!repository.uniqueUsername(username)) throw new RepeatedUsernameException();
        User user = new User (username, name);
        return repository.insert(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return repository.getUserByUsername(username);
    }

    @Override
    public Property createProperty(String name, String description, double price, int capacity, int rooms, String cityName) {
        City city = repository.getCityByName(cityName);
        if (city == null){
            city = new City(cityName);
            repository.insert(city);
        }
        Property property = new Property(name, description, price, capacity, city);
        return repository.insert(property);
    }

    @Override
    public City getCityByName(String name) {
        return repository.getCityByName(name);
    }

    @Override
    public List<Reservation> getReservationsForProperty(String propertyId) {
        return repository.getReservationsForProperty(propertyId);
    }

    @Override
    public List<City> getCitiesMatching(String content) {
        return null;
    }

    @Override
    public City registerCity(String name) {
        return null;
    }

    @Override
    public List<City> getAllCities() {
        return null;
    }

    @Override
    public List<ReservationCount> getReservationCountByStatus() {
        return null;
    }
}
