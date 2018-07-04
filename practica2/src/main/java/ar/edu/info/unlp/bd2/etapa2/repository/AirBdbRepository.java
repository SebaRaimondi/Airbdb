package ar.edu.info.unlp.bd2.etapa2.repository;

import ar.edu.info.unlp.bd2.etapa2.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class AirBdbRepository {

    @Autowired
    MongoTemplate mongoTemplate;



    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public User createUser(User user){
        mongoTemplate.save(user, "user");
        return user;
    }

    /* returns true if a given username isnt used yet  */
    public boolean uniqueUsername(String username){
        User user = mongoTemplate.findOne(
                Query.query(Criteria.where("username").is(username)), User.class);
        return user == null;
    }

    public void clearDb() {
        mongoTemplate.getDb().drop();
    }
}
