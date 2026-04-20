package com.srtr.models;

import java.util.Map;
import java.util.Set;

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

@Node("postSearchData")
public class PostSearchData {
    @Id
    private String id;
    private Set<Skill> skillsMatcher;
    private Map<Skill, Integer> skillsData;
    private Integer averageExp;
}
