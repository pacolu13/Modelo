package com.srtr.repositories;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.srtr.models.Connection;

@Repository
public interface ConnectionRepository extends MongoRepository<Connection, String> {

    List<Connection> getAllConnectionByFromUserId(String userId);

    List<Connection> getlAllConnectionsListById(List<String> connections);

}
