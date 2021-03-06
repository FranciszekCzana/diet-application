package com.springboot.dietapplication.repository.mongo;

import com.springboot.dietapplication.model.mongo.properties.MongoFoodProperties;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoFoodPropertiesRepository extends MongoRepository<MongoFoodProperties, String> {

}
