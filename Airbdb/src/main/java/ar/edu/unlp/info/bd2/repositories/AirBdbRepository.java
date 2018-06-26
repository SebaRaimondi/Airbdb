package ar.edu.unlp.info.bd2.repositories;

import ar.edu.unlp.info.bd2.model.*;
import org.hibernate.*;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AirBdbRepository {

  @Autowired
  SessionFactory sessionFactory;

  /* ------------------------------   ETAPA 1  ------------------------------ */


  /* saves a new user and returns it */
  public User storeUser(User user){
    sessionFactory.getCurrentSession().save(user);
    return user;
  }


  /* returns true if a given username isnt used yet  */
  public boolean uniqueUsername(String username){
    String stmt = "SELECT u FROM User u WHERE u.username = :username";
    Session session = sessionFactory.getCurrentSession();
    TypedQuery<User> query = session.createQuery(stmt, User.class);
    query.setParameter("username", username);
    List<User> results = query.getResultList();
    return results.isEmpty();
  }


  /* returns an existing user by email, null otherwise */
  public User getUserByUsername(String email) {
    String stmt = "SELECT u FROM User u WHERE u.username = :email";
    Session session = sessionFactory.getCurrentSession();
    TypedQuery<User> query = session.createQuery(stmt, User.class);
    query.setParameter("email", email);
    return ((Query<User>) query).uniqueResult();
  }


  /* saves a new apartment and returns it */
  public Apartment storeApartment(Apartment apartment) {
      sessionFactory.getCurrentSession().save(apartment);
      return apartment;
  }


  /* searchs for a city given a name. returns it or null if the city doesn exist */
  public City findCityByName(String cityName){
    Session session = sessionFactory.getCurrentSession();
    String stmt = "SELECT c FROM City c WHERE c.name = :cityName";
    TypedQuery<City> query = session.createQuery(stmt, City.class);
    query.setParameter("cityName", cityName);
    return ((Query<City>) query).uniqueResult();
  }


  /* creates a city given a name */
  public City storeCity(City city){
    sessionFactory.getCurrentSession().save(city);
    return city;
  }


  /* saves a new room and returns it */
  public PrivateRoom storeRoom(PrivateRoom room) {
    sessionFactory.getCurrentSession().save(room);
    return room;
  }


  /* returns a property by a given name, null otherwise */
  public Property getPropertyByName(String name) {
    String stmt = "SELECT p FROM Property p WHERE p.name = :name";
    Session session = sessionFactory.getCurrentSession();
    TypedQuery<Property> query = session.createQuery(stmt, Property.class);
    query.setParameter("name", name);
    return ((Query<Property>) query).uniqueResult();
  }


  /* saves a new reservation and returns it */
  public Reservation storeReservation(Reservation reservation){
    sessionFactory.getCurrentSession().save(reservation);
    return reservation;
  }


  /* returns true if a reservation for an apartment can be made in a period, false otherway */
  public boolean isPropertyAvailable(Long id, Date from, Date to) {
    Session session = sessionFactory.getCurrentSession();
    String stmt = "FROM Reservation r WHERE r.id = :apartmentId AND r.status <> :canceled AND ((r.from BETWEEN :from AND :to OR r.to BETWEEN :from  AND :to) OR (:from BETWEEN r.from AND r.to OR :to BETWEEN r.from  AND r.to))";
    Query query = session.createQuery(stmt);
    query.setParameter("apartmentId", id);
    query.setParameter("from", from);
    query.setParameter("to", to);
    query.setParameter("canceled", ReservationStatus.CANCELED);
    List<Reservation> results = query.getResultList();

    System.out.println("!!!! Cant. de reservas que se superponen: " + results.size());
    System.out.println("!!!! Especificación de las reservas que se superponen: " + results);
    System.out.println("!!!! En repository isPropertyAvailable devuelvo: " + results.isEmpty());
    return results.isEmpty();
  }


  /* returns an existing user by id, null otherwise */
  public User getUserById(Long id) { return sessionFactory.getCurrentSession().get(User.class, id); }


  /* returns an existing user by id, null otherwise */
  public Reservation getReservationById(Long id) { return sessionFactory.getCurrentSession().get(Reservation.class, id); }


  /* returns an existing property by id, null otherwise */
  public Property getPropertyById(long id) {
    return sessionFactory.getCurrentSession().get(Property.class, id);
  }

  public void cancelReservation(long reservationId) {
    sessionFactory.getCurrentSession().persist(this.getReservationById(reservationId).cancelReservation());
  }

  public ReservationRating storeRating(ReservationRating rating) {
    sessionFactory.getCurrentSession().save(rating);
    return rating;
  }

  public void finishReservation(long reservationId) {
    sessionFactory.getCurrentSession().persist(this.getReservationById(reservationId).finishReservation());
  }

  public ReservationRating getRatingForReservation(Long id) {
    return this.getReservationById(id).getRating();
  }


  /* ------------------------------   ETAPA 2  ------------------------------ */

  public List<Property> getAllPropertiesReservedByUser(String userEmail) {
    Session session = sessionFactory.getCurrentSession();
    String stmt = "SELECT prop FROM Reservation r, User u, Property prop WHERE u.username=:username AND r.user.id = u.id AND r.apartment.id = prop.id";
    Query query = session.createQuery(stmt);
    query.setParameter("username", userEmail);
    List<Property> results = query.getResultList();

    System.out.println("!!!! En repository getAllPropertiesReservedByUser " + userEmail +" devuelvo: " + results);
    return results;
  }


  public List<User> getUsersSpendingMoreThan(double amount) {
    Session session = sessionFactory.getCurrentSession();
    String stmt = "SELECT u FROM Reservation r, User u WHERE r.user = u  GROUP BY u HAVING sum(r.apartment.price * (r.to - r.from)) > :amount";
    Query query = session.createQuery(stmt);
    query.setParameter("amount", amount);
    List<User> results = query.getResultList();

    System.out.println("!!!! En repository getUsersSpendingMoreThan " + amount +" devuelvo: " + results);
    return results;
  }

  public List<Object[]> getApartmentTop3Ranking(){
    Session session = sessionFactory.getCurrentSession();
    String stmt = "SELECT a, AVG(rr.points) FROM Reservation r, ReservationRating rr, Property a GROUP BY a ORDER BY AVG(rr.points) DESC";
    Query query = session.createQuery(stmt);
    query.setMaxResults(3);
    List<Object[]> results = query.getResultList();

    System.out.println("!!!! En repository getApartmentTop3Ranking devuelvo: " + results);
    return results;
  }

  public List<User> getUsersThatReservedMoreThan1PropertyDuringASpecificYear(int year){
    Session session = sessionFactory.getCurrentSession();
    String stmt = "SELECT u FROM Reservation r, User u WHERE r.user = u AND YEAR(r.from) = :year AND YEAR(r.to) = :year GROUP BY u HAVING COUNT(*) > 1";
    Query query = session.createQuery(stmt);
    query.setParameter("year", year);
    List<User> results = query.getResultList();

    System.out.println("!!!! En repository getUsersThatReservedMoreThan1PropertyDuringASpecificYear devuelvo: " + results);
    return results;
  }

  public List<Property> getPropertiesThatHaveBeenReservedByMoreThanOneUserWithCapacityMoreThan(int capacity) {
    Session session = sessionFactory.getCurrentSession();
    String stmt = "SELECT r FROM Reservation r WHERE r.apartment.capacity > :capacity GROUP BY r.apartment HAVING COUNT(DISTINCT r.user ) > 1";
    Query query = session.createQuery(stmt);
    query.setParameter("capacity", capacity);
    List<Property> results = query.getResultList();

    System.out.println("!!!! En repository getPropertiesThatHaveBeenReservedByMoreThanOneUserWithCapacityMoreThan devuelvo: " + results);
    return results;
  }

  public List<Reservation> getReservationsInCitiesForUser(String username, String[] cities) {
    ArrayList<String> list = new ArrayList<String>();
    for (Object c : cities) {
      list.add(c.toString());
    }
    Session session = sessionFactory.getCurrentSession();
    String stmt = "SELECT r.apartment FROM Reservation r WHERE r.user.username = :username AND r.apartment.city.name IN (:cities)";
    Query query = session.createQuery(stmt);
    query.setParameter("username", username);
    query.setParameter("cities", list);
    List<Reservation> results = query.getResultList();

    System.out.println("!!!! En repository getReservationsInCitiesForUser devuelvo: " + results);
    return results;
  }

}
