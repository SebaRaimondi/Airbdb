package ar.edu.unlp.info.bd2.repositories;

import ar.edu.unlp.info.bd2.model.*;
import org.hibernate.*;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

public class AirBdbRepository {

  @Autowired
  SessionFactory sessionFactory;

  /* saves a new user and returns it */
  public User storeUser(User user){
    sessionFactory.getCurrentSession().save(user);
    return user;
  }


  /* returns true if a given username isnt used yet  */
  public boolean uniqueUsername(String username){
    String sql = "SELECT u FROM User u WHERE u.username = :username";
    Session session = sessionFactory.getCurrentSession();
    TypedQuery<User> query = session.createQuery(sql, User.class);
    query.setParameter("username", username);
    List<User> results = query.getResultList();
    return results.isEmpty();
  }


  /* returns an existing user by email, null otherwise */
  public User getUserByUsername(String email) {
    String sql = "SELECT u FROM User u WHERE u.username = :email";
    Session session = sessionFactory.getCurrentSession();
    TypedQuery<User> query = session.createQuery(sql, User.class);
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
    String sql = "SELECT c FROM City c WHERE c.name = :cityName";
    TypedQuery<City> query = session.createQuery(sql, City.class);
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
    String sql = "SELECT p FROM Property p WHERE p.name = :name";
    Session session = sessionFactory.getCurrentSession();
    TypedQuery<Property> query = session.createQuery(sql, Property.class);
    query.setParameter("name", name);
    return ((Query<Property>) query).uniqueResult();
  }

  /* saves a new reservation and returns it */
  public Reservation createReservation(long apartmentId, long userId, Date from, Date to) throws ReservationException{
    Session session = sessionFactory.openSession();
    Transaction tx = null;
    Reservation reservation = null;

    try {
      tx = session.beginTransaction();

      if (! this.isPropertyAvailable(apartmentId, from, to, session) ) throw new ReservationException();

      Apartment apartment = (Apartment) session.get(Apartment.class, apartmentId);
      User user = (User) session.get(User.class, userId);

      reservation = new Reservation(from, to, apartment, user);
      session.save(reservation);

      tx.commit();
    }
    catch (Exception e) {
      if (tx!=null) tx.rollback();
      throw e;
    }
    finally {
      session.close();
    }

    return reservation;
  }


  /* returns true if a reservation for an apartment can be made in a period, false otherway */
  public boolean isPropertyAvailable(Long id, Date from, Date to, Session session) {
    List<Reservation> results = null;

    TypedQuery<Reservation> query =
            session.createQuery("SELECT r FROM Reservation r WHERE r.id = :apartmentId AND (r.from <= :from AND r.to >= :from) OR (r.from <= :to AND r.from >= :from) ", Reservation.class);
    query.setParameter("apartmentId", id);
    query.setParameter("from", from);
    query.setParameter("to", to);
    results = query.getResultList();

    return results.isEmpty();

  }


  /* returns true if a reservation for an apartment can be made in a period, false otherway */
  public boolean isPropertyAvailable(Long id, Date from, Date to) {
    Session session = sessionFactory.openSession();
    Transaction tx = null;
    List<Reservation> results = null;

    TypedQuery<Reservation> query =
            session.createQuery("SELECT r FROM Reservation r WHERE r.id = :apartmentId AND (r.from <= :from AND r.to >= :from) OR (r.from <= :to AND r.from >= :from) ", Reservation.class);
    query.setParameter("apartmentId", id);
    query.setParameter("from", from);
    query.setParameter("to", to);
    results = query.getResultList();

    return results.isEmpty();

  }


  /* returns an existing user by id, null otherwise */
  public User getUserById(Long id) {
    Session session = sessionFactory.openSession();
    Transaction tx = null;
    User user;

    try {
      tx = session.beginTransaction();
      TypedQuery<User> query = session.createQuery("SELECT u FROM User u WHERE u.id = :id", User.class);
      user = query.setParameter("id", id).getSingleResult();
      tx.commit();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } finally {
      session.close();
    }

    return user;
  }













}
