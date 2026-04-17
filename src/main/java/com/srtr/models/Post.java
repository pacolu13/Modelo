package com.srtr.models;

import java.time.LocalDate;
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


@Document(collection = "publications")
public class Post {
    @Id
    private String id;
    private String userId;
    private String title; 
    private String description;
    private String body;
    private String type;
    private List<String> tags;
    private LocalDate createdAt;
}
