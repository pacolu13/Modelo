package com.srtr.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Lombok generate getters, setters, toString, equals, hashCode methods
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Document(collection = "jobs")
public class Job {

    @Id
    private String id;
    private String title;
    private String company;
    private Integer requiredExp, desirableExp;
    private List<SeniorityType> requiredSeniority, desirableSeniority; // List of Seniority Type Enum for the job
    private List<String> requiredSkills, desirableSkills; // List of skill IDs required for the job
    private List<String> aplicants; // List of user IDs who have applied for the job
}
