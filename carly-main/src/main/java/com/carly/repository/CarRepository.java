package com.carly.repository;

import com.carly.model.collection.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CarRepository extends MongoRepository<User, String> {
}
