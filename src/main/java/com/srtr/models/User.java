package com.srtr.models;

import java.util.List;

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

@Document(collection = "users")
public class User {

    @Id
    private String id;
    private String name;
    private SeniorityType seniority;
    private Integer experienceYears;
    private String email;
    private List<Skill> skills; // List of skill IDs possessed by the user
    private List<String> connections; // List of user IDs who are connected to this user
    private List<String> appliedJobs; // List of job IDs the user has applied for
    private List<String> posts;
}
