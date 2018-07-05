package ar.edu.info.unlp.bd2.etapa2.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;
import ar.edu.info.unlp.bd2.etapa2.model.City;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends MongoRepository < City, String >{

    // así sería un find by name like implementado, pero como que puedo escribir metodos que se hacen solos como abajo
    /*    @Query("{ 'name' : { $regex: ?0 } }")
          List<City> getCitiesMatching(String name);    */

    List<City> findByNameLikeOrderByNameAsc(String name);
}
