package ar.edu.unlp.info.bd2.services;

import ar.edu.unlp.info.bd2.model.*;
import ar.edu.unlp.info.bd2.repositories.AirBdbRepository;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.TypedQuery;
import java.util.List;


public class AirBdbServiceImpl implements AirBdbService {
    private AirBdbRepository repository;

    public AirBdbServiceImpl(AirBdbRepository repository) {
        this.repository = repository;
    }

    /* saves a new user and returns it */
    public User createUser(String username, String name){
        Session session = repository.sessionFactory.openSession();
        Transaction tx = null;
        User user = null;

        try {
            tx = session.beginTransaction();
            user = new User(username, name);
            session.save(user);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return user;
    }

    /* returns an user by a given email, null otherwise */
    public User getUserByUsername(String email) {
        Session session = repository.sessionFactory.openSession();
        Transaction tx = null;
        User us = null;

        try {
            tx = session.beginTransaction();
            TypedQuery<User> query =
                    session.createQuery("SELECT u FROM User u WHERE u.username = :email", User.class);
            List<User> results = query.setParameter("email", email).getResultList();
            us = results.get(0);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return us;
    }

    /* saves a new city and returns it */
    public City createCity(String name){
        Session session = repository.sessionFactory.openSession();
        Transaction tx = null;
        City city = null;

        try {
            tx = session.beginTransaction();
            city = new City(name);
            session.save(city);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return city;
    }

    /* returns a city given a name. can return null */
    public City getCityByName(String name){
        Session session = repository.sessionFactory.openSession();
        Transaction tx = null;
        City city = null;

        try {
            tx = session.beginTransaction();
            TypedQuery<City> query =
                    session.createQuery("SELECT c FROM City c WHERE c.name = :name", City.class);
            List<City> results = query.setParameter("name", name).getResultList();
            city = results.isEmpty() ? null : results.get(0);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return city;
    }

    /* saves a new apartment and returns it */
    public Apartment createApartment(String name, String description, double price, int capacity, int rooms, String cityName){
        Session session = repository.sessionFactory.openSession();
        Transaction tx = null;
        Apartment apartment = null;
        City city = this.getCityByName(cityName);

        /* so that you can add an apartment in a city that isnt uploaded yet */
        /* we create the city */
        if (city == null) {
           city = this.createCity(cityName);
        }

        try {
            tx = session.beginTransaction();
            apartment = new Apartment(name, description, price, capacity, rooms, city);
            session.save(apartment);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return apartment;
    }

    /* returns a property by a given name, null otherwise */
    public Property getPropertyByName(String name) {
        Session session = repository.sessionFactory.openSession();
        Transaction tx = null;
        Property property = null;

        try {
            tx = session.beginTransaction();
            TypedQuery<Property> query =
                    session.createQuery("SELECT p FROM Property p WHERE p.name = :name", Property.class);
            List<Property> results = query.setParameter("name", name).getResultList();
            property = results.get(0);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return property;
    }

    /* saves a new room and returns it */
    public PrivateRoom createRoom(String name, String description, double price, int capacity, int beds, String cityName){
        Session session = repository.sessionFactory.openSession();
        Transaction tx = null;
        PrivateRoom room = null;
        City city = this.getCityByName(cityName);

        /* so that you can add an room in a city that isnt uploaded yet */
        /* we create the city */
        if (city == null) {
            city = this.createCity(cityName);
        }

        try {
            tx = session.beginTransaction();
            room = new PrivateRoom(name, description, price, capacity, beds, city);
            session.save(room);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return room;
    }

}

