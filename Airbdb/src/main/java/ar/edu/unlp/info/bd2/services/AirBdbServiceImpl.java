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

    /* returns an user by email */
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


}

