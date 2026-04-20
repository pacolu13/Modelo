package com.srtr.repositories;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import com.srtr.models.Recomendation;

@Repository
public interface RecommendationRepository extends Neo4jRepository<Recomendation, String> {

}
