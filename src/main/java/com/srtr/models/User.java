package com.srtr.models;

import java.util.Set;

import org.neo4j.ogm.annotation.Relationship;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Node("User") // Define que esto es un nodo de tipo Usuario
public class User {

    @Id
    private String id;
    private String name;
    private SeniorityType seniority;
    private Integer experienceYears;
    private String email;

    // RELACIONES: Reemplazamos las List<String> por Sets de objetos con @Relationship

    @Relationship(type = "HAS_SKILL", direction = Relationship.Direction.OUTGOING)
    private Set<Skill> skills; 

    @Relationship(type = "CONNECTED_TO", direction = Relationship.Direction.UNDIRECTED)
    private Set<User> connections;

    @Relationship(type = "APPLIED_TO", direction = Relationship.Direction.OUTGOING)
    private Set<Job> appliedJobs;

    @Relationship(type = "AUTHORED", direction = Relationship.Direction.OUTGOING)
    private Set<Post> posts;
}