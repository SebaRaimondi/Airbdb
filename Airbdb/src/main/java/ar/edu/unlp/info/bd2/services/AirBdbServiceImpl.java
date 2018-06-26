package ar.edu.unlp.info.bd2.services;

import ar.edu.unlp.info.bd2.model.*;
import ar.edu.unlp.info.bd2.repositories.AirBdbRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


public class AirBdbServiceImpl implements AirBdbService {
    private AirBdbRepository repository;

    public AirBdbServiceImpl(AirBdbRepository repository) {
        this.repository = repository;
    }

    /* ------------------------------  ETAPA 1  ------------------------------ */

    @Transactional
    /* creates a new user and returns it. throws UsernameException if the chosen username is already taken */
    public User createUser(String username, String name) throws RepeatedUsernameException{
        username = username.toLowerCase();
        if (! this.repository.uniqueUsername(username)) throw new RepeatedUsernameException();
        User user = new User (username, name);
        return repository.storeUser(user);
    }

    @Transactional
    /* returns an user by a given email, null otherwise */
    public User getUserByUsername(String email) {
        email = email.toLowerCase();
        return repository.getUserByUsername(email);
    }


    @Transactional
    /* creates a new apartment and returns it */
    public Apartment createAparment(String name, String description, double price, int capacity, int rooms, String cityName){
        cityName = cityName.toUpperCase();
        City city = repository.findCityByName(cityName);
        if (city == null){
            city = new City(cityName);
            repository.storeCity(city);
        }
        Apartment apartment = new Apartment(name, description, price, capacity, rooms, city);
        return repository.storeApartment(apartment);
    }

    @Transactional
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
        Property apartment = (Property) repository.getPropertyById(apartmentId);
        User user = repository.getUserById(userId);
        Reservation reservation = new Reservation(apartment, user, from, to);
        return repository.storeReservation(reservation);
    }

    @Transactional
    /* returns an user by a given id, null otherwise */
    public User getUserById(Long id) {
        return repository.getUserById(id);
    }

    @Transactional
    /* returns an user by a given id, null otherwise */
    public boolean isPropertyAvailable(Long id, Date from, Date to){
        return repository.isPropertyAvailable(id, from, to);
    }

    @Transactional
    /* returns an user by a given id, null otherwise */
    public Reservation getReservationById(Long id) {
        return repository.getReservationById(id);
    }

    @Transactional
    public void cancelReservation(Long reservationId) {
        repository.cancelReservation(reservationId);
    }

    @Transactional
    public ReservationRating createRating(Reservation reservation, int points, String comment) {
        ReservationRating rating = new ReservationRating(reservation, points, comment);
        return repository.storeRating(rating);
    }

    @Transactional
    public void rateReservation(Long reservationId, int points, String comment) throws RateException {
        Reservation reservation = this.getReservationById(reservationId);
        if (!reservation.getStatus().equals(ReservationStatus.FINISHED)) throw new RateException();
        this.createRating(reservation, points, comment);
    }

    @Transactional
    public void finishReservation(Long id) {
        repository.finishReservation(id);
    }

    @Transactional
    public ReservationRating getRatingForReservation(Long reservationId) {
        return repository.getRatingForReservation(reservationId);
    }

    /* ------------------------------   ETAPA 2  ------------------------------ */

    @Transactional
    public List<Property> getAllPropertiesReservedByUser(String userEmail) {
        return repository.getAllPropertiesReservedByUser(userEmail);
    }

    @Transactional
    public List<User> getUsersSpendingMoreThan(double amount){
        return getUsersSpendingMoreThan(amount);

    }

    @Transactional
    public List<Object[]> getApartmentTop3Ranking(){
        return repository.getApartmentTop3Ranking();
    }

    @Transactional
    public List<User> getUsersThatReservedMoreThan1PropertyDuringASpecificYear(int year){
        return repository.getUsersThatReservedMoreThan1PropertyDuringASpecificYear(year);
    }

    @Transactional
    public List<Property> getPropertiesThatHaveBeenReservedByMoreThanOneUserWithCapacityMoreThan(int capacity){
        return repository.getPropertiesThatHaveBeenReservedByMoreThanOneUserWithCapacityMoreThan(capacity);
    }

    @Transactional
    public List<Reservation> getReservationsInCitiesForUser(String username, String... cities) {
        return repository.getReservationsInCitiesForUser(username, cities);
    }
}