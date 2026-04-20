package com.srtr.models;

import java.time.LocalDate;
import java.util.Set;

import org.neo4j.ogm.annotation.Relationship;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Node;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Node("Post")
public class Post {

    @Id
    private String id;
    private String title;
    private String description;
    private String body;
    private String type; // Ej: "Informativo", "Búsqueda", etc.
    private LocalDate createdAt;

    @Relationship(type = "AUTHORED_BY", direction = Relationship.Direction.OUTGOING)
    private User author; // Ya no es String userId

    @Relationship(type = "TAGGED_WITH", direction = Relationship.Direction.OUTGOING)
    private Set<Skill> tags; // Ahora puedes navegar de un Post a una Skill
}
