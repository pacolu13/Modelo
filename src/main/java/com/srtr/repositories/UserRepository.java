package com.srtr.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.srtr.models.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

}
