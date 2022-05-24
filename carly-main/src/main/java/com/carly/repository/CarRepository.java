package com.carly.repository;

import com.carly.model.collection.Car;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CarRepository extends MongoRepository<Car, String> {
    List<Car> findByOwnerId(String ownerId);
}
