package com.carly.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * Used for testing purposes.
 */
@RestController
@RequestMapping("/api/test")
@ConditionalOnProperty(
        value = "testMode",
        havingValue = "true")
public class TestController {

    @Autowired
    MongoTemplate mongoTemplate;

    @GetMapping("/collections")
    public Set<String> getAllMongoCollections() {
        return mongoTemplate.getCollectionNames();
    }

    @GetMapping("/collections/count/{dbCollection}")
    public long countMongoCollection(@PathVariable String dbCollection) {
        return mongoTemplate.getCollection(dbCollection).countDocuments();
    }

}