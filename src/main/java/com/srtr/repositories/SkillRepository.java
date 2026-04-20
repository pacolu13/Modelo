package com.srtr.repositories;

import java.util.List;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import com.srtr.models.Skill;

@Repository
public interface SkillRepository extends Neo4jRepository<Skill, String> {

    List<Skill> getAllSkillsByUserId(String id);

    List<Skill> getAllSkillsByName(String name);
}
