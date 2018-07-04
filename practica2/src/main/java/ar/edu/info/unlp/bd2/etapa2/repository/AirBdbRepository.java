package ar.edu.info.unlp.bd2.etapa2.repository;

import ar.edu.info.unlp.bd2.etapa2.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

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

    public User createUser(User user){
        mongoTemplate.insert(user);
        return user;
    }

    /* returns true if a given username isnt used yet  */
    public boolean uniqueUsername(String username){
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));
        List<User> users = mongoTemplate.find(query, User.class);
        System.out.println("!!!! En repository uniqueUsername encuentra " + users);
        return users.isEmpty();
    }

    public User getUserByUsername(String username) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));
        User users = mongoTemplate.findOne(query, User.class);
        return users;
    }
}
