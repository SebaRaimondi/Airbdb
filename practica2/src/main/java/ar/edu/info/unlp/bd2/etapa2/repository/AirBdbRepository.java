package ar.edu.info.unlp.bd2.etapa2.repository;

import ar.edu.info.unlp.bd2.etapa2.model.City;
import ar.edu.info.unlp.bd2.etapa2.model.Property;
import ar.edu.info.unlp.bd2.etapa2.model.Reservation;
import ar.edu.info.unlp.bd2.etapa2.model.User;
import ar.edu.info.unlp.bd2.etapa2.utils.ReservationCount;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

public class AirBdbRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void clearDb() {
        mongoTemplate.getDb().drop();
    }

    public <T> T insert(T entity) {
        mongoTemplate.insert(entity);
        return entity;
    }

    public <T> T save(T entity) {
        mongoTemplate.save(entity);
        return entity;
    }

    /* returns true if a given username isnt used yet  */
    public boolean uniqueUsername(String username){
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));
        List<User> users = mongoTemplate.find(query, User.class);
        return users.isEmpty();
    }

    public User getUserByUsername(String username) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));
        User users = mongoTemplate.findOne(query, User.class);
        return users;
    }

    public City getCityByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        City city = mongoTemplate.findOne(query, City.class);
        return city;
    }

    public List<Reservation> getReservationsForProperty(String propertyId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("property.$id").is(new ObjectId(propertyId)));
        List<Reservation> reservations = mongoTemplate.find(query, Reservation.class);
        return reservations;
    }

    public boolean isPropertyAvailable(String propertyId, Date from, Date to) {
        Criteria criteria1 = Criteria.where("from").gte(from).lt(to);
        Criteria criteria2 = Criteria.where("to").gte(from).lt(to);
        Query query = new Query(new Criteria().orOperator(criteria1, criteria2));
        query.addCriteria(Criteria.where("property.$id").is(new ObjectId(propertyId)));
        List<Reservation> properties = mongoTemplate.find(query, Reservation.class);
        return properties.isEmpty();
    }

    public Property getPropertyById(String propertyId) {
        return mongoTemplate.findById(propertyId, Property.class);
    }

    public User getUserById(String userId) {
        User user = mongoTemplate.findById(userId, User.class);
        return user;
    }

    // usar aggregation
    public List<ReservationCount> getReservationCountByStatus(){

        GroupOperation groupByStatusAndSumCount = group("status")
                .count().as("count").first("status").as("status");

        ProjectionOperation projectToMatchModel = project("status");

        Aggregation aggregation = newAggregation(projectToMatchModel, groupByStatusAndSumCount);

        AggregationResults<ReservationCount> results = mongoTemplate.aggregate(
                aggregation, Reservation.class, ReservationCount.class
        );

        return results.getMappedResults();
    }
}
