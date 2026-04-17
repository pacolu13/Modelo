package com.srtr.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.srtr.models.Skill;

@Repository
public interface SkillRepository extends MongoRepository<Skill, String> {

    List<Skill> getAllSkillsByUserId(String id);

    List<Skill> getAllSkillsByName(String name);
}
