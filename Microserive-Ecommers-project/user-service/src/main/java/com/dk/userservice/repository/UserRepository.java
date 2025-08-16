package com.dk.userservice.repository;

import com.dk.userservice.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository  extends MongoRepository<User ,String> {
    Optional<User> findByUserName(String userName);

}
