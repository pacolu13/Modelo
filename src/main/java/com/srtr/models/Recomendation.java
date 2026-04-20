package com.srtr.models;

import java.time.LocalDate;

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

@Node("recomendation")
public class Recomendation {

    @Id
    private String id;
    private String userId;
    private String type;
    private String referenceId;
    private double score;
    private LocalDate createdAt;
}
