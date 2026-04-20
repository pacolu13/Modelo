package com.srtr.repositories;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import com.srtr.models.Job;


@Repository
public interface JobRepository extends Neo4jRepository<Job, String> {

}
