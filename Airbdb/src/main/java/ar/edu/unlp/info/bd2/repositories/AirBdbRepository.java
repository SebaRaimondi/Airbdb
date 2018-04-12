package ar.edu.unlp.info.bd2.repositories;

import ar.edu.unlp.info.bd2.model.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

public class AirBdbRepository {

  @Autowired
  SessionFactory sessionFactory;

  /* saves a new user and returns it */
  public User saveUser(User user) {
    Session session = sessionFactory.openSession();
    Transaction tx = null;

    try {
      tx = session.beginTransaction();
      session.save(user);
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }

    return user;
  }


  /* returns an existing user by email, null otherwise */
  public User getUserByUsername(String email) {
    Session session = sessionFactory.openSession();
    Transaction tx = null;
    User user;

    try {
      tx = session.beginTransaction();
      TypedQuery<User> query = session.createQuery("SELECT u FROM User u WHERE u.username = :email", User.class);
      user = query.setParameter("email", email).getSingleResult();
      tx.commit();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } finally {
      session.close();
    }

    return user;
  }


  /* saves a new apartment and returns it */
  public Apartment saveApartment(String name, String description, double price, int capacity, int rooms, String cityName) {
    Session session = sessionFactory.openSession();
    Transaction tx = null;
    City city;
    Apartment apartment = null;

    try {
      tx = session.beginTransaction();

      city = this.manageCity(cityName, session);
      apartment = new Apartment(name, description, price, capacity, rooms, city);
      session.save(apartment);

      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }

    return apartment;
  }


  /* returns an existing city by name, or creates a new one and returns it */
  private City manageCity(String name, Session session){
    City city = null;

    TypedQuery<City> query = session.createQuery("SELECT c FROM City c WHERE c.name = :name", City.class);
    List<City> results = query.setParameter("name", name).getResultList();
    city = results.isEmpty() ? null : results.get(0);

    if (city == null) {
      city = new City(name);
      session.save(city);
    }

    return city;
  }


  /* saves a new room and returns it */
  public PrivateRoom saveRoom(String name, String description, double price, int capacity, int beds, String cityName) {
    Session session = sessionFactory.openSession();
    Transaction tx = null;
    City city;
    PrivateRoom room = null;

    try {
      tx = session.beginTransaction();

      city = this.manageCity(cityName, session);
      room = new PrivateRoom(name, description, price, capacity, beds, city);
      session.save(room);

      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }

    return room;
  }


  /* returns a property by a given name, null otherwise */
  public Property getPropertyByName(String name) {
    Session session = sessionFactory.openSession();
    Transaction tx = null;
    Property property = null;

    try {
      tx = session.beginTransaction();
      TypedQuery<Property> query = session.createQuery("SELECT p FROM Property p WHERE p.name = :name", Property.class);
      property = query.setParameter("name", name).getSingleResult();
      tx.commit();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } finally {
      session.close();
    }

    return property;
  }

  /* saves a new reservation and returns it */
  public Reservation saveReservation(long apartmentId, long userId, Date from, Date to) throws ReservationException{
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


  /* if */
  public boolean isPropertyAvailable(Long id, Date from, Date to, Session session) {
    List<Reservation> results = null;

    TypedQuery<Reservation> query =
            session.createQuery("SELECT r FROM Reservation r WHERE r.apartment = :apartmentId AND r.from = :from AND r.to = :to ", Reservation.class);
    query.setParameter("apartmentId", id);
    query.setParameter("from", from);
    query.setParameter("to", to);
    results = query.getResultList();

    return results.isEmpty();

  }













}
