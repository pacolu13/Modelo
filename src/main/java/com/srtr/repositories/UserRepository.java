package com.srtr.repositories;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import com.srtr.models.User;

@Repository
public interface UserRepository extends Neo4jRepository<User, String> {

}
