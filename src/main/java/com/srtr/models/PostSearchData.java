package com.srtr.models;

import java.util.List;
import java.util.Map;

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

@Document(collection = "postsSearchData")
public class PostSearchData {
    @Id
    private String id;
    private List<Skill> skillsMatcher;
    private Map<Skill, Integer> skillsData;
    private Integer averageExp;
}
