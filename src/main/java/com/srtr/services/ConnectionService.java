package com.srtr.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.srtr.models.Connection;
import com.srtr.repositories.ConnectionRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ConnectionService {

    private final ConnectionRepository connectionRepository;

    public List<Connection> getConnections() {
        return connectionRepository.findAll();
    }

    public Connection getConnectionById(String id) {
        return connectionRepository.findById(id).orElse(null);
    }

    public Connection updateConnection(String id, Connection updatedConnection) {
        return connectionRepository.findById(id).map(connection -> {
            connection.setStatus(updatedConnection.getStatus());
            connection.setFromUser(updatedConnection.getFromUser());
            connection.setToUser(updatedConnection.getToUser());
            return connectionRepository.save(connection);
        }).orElse(null);
    }

    public void deleteConnection(String id) {
        connectionRepository.deleteById(id);
    }

    public List<Connection> getAllConnectionByFromUserId(String userId){
        return connectionRepository.getAllConnectionByFromUserId(userId);
    }

    public List<Connection> getAllConnectionsById(List<String> connections) {
        return connectionRepository.getlAllConnectionsListById(connections);
    }

}
