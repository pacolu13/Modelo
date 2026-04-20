package com.srtr.models;

import java.time.LocalDate;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@RelationshipProperties
public class Connection {
    @Id @GeneratedValue private Long id;
    @TargetNode private User toUser;
    private String status; // "pending", "accepted"
    private LocalDate since;
}