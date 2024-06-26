package com.carly.repository;

import com.carly.enumeration.ERole;
import com.carly.model.collection.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}