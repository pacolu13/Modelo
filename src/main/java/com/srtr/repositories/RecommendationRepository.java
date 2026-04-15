package com.srtr.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.srtr.models.Recomendation;

@Repository
public interface RecommendationRepository extends MongoRepository<Recomendation, String> {

}
