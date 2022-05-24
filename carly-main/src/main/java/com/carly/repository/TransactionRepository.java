package com.carly.repository;

import com.carly.model.collection.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TransactionRepository extends MongoRepository<Transaction, String> {

	List<Transaction> findByBuyerId(String buyerId);
}
