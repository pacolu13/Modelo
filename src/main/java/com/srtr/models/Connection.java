package com.srtr.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Document(collection = "connections")
public class Connection {

    @Id
    private String id;
    private String fromUser;
    private String toUser;
    private String status; // "pending", "accepted", "rejected"
}
