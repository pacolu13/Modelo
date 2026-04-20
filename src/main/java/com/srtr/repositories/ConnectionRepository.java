package com.srtr.repositories;


import java.util.List;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import com.srtr.models.Connection;

@Repository
public interface ConnectionRepository extends Neo4jRepository<Connection, Long> {

    List<Connection> getAllConnectionByFromUserId(String userId);

    List<Connection> getlAllConnectionsListById(List<String> connections);

}
