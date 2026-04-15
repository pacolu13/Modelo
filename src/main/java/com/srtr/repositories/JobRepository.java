package com.srtr.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.srtr.models.Job;


@Repository
public interface JobRepository extends MongoRepository<Job, String> {

}
