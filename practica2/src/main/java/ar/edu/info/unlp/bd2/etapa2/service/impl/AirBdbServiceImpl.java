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
        return null;
    }

    @Override
    public User getUserById(String id) {
        return null;
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
        username = username.toLowerCase();
        if (! this.repository.uniqueUsername(username)) throw new RepeatedUsernameException();
        User user = new User (username, name);
        return repository.createUser(user);
    }

    @Override
    public User getUserByUsername(String email) {
        return null;
    }

    @Override
    public Property createProperty(String name, String description, double price, int capacity, int rooms, String cityName) {
        return null;
    }

    @Override
    public City getCityByName(String name) {
        return null;
    }

    @Override
    public List<Reservation> getReservationsForProperty(String propertyId) {
        return null;
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
