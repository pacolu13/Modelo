package com.srtr.models;

import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Lombok generate getters, setters, toString, equals, hashCode methods
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Node("Job")
public class Job {

    @Id
    private String id;
    private String title;
    private String company;
    private Integer requiredExp, desirableExp;

    // El Seniority puede ser un atributo o un Nodo si quieres filtrar rápido
    private List<SeniorityType> requiredSeniority, desirableSeniority;

    @Relationship(type = "REQUIRES_SKILL", direction = Relationship.Direction.OUTGOING)
    private Set<Skill> skills; // Aquí usaremos una clase de relación para distinguir "Required" de "Desirable"

    @Relationship(type = "HAS_APPLICANT", direction = Relationship.Direction.INCOMING)
    private Set<User> applicants;
}
