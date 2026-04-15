package com.srtr.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.srtr.models.Skill;
import com.srtr.repositories.SkillRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class SkillService {

    private final SkillRepository skillRepository;

    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    public Skill getSkillById(String id) {
        return skillRepository.findById(id).orElse(null);  
    }

    public Skill updateSkill(String id, Skill skillDetails) {
        Skill skill = getSkillById(id);
        skill.setName(skillDetails.getName());
        skill.setCategory(skillDetails.getCategory());
        return skillRepository.save(skill);
    }

    public void deleteSkill(String id) {
        Skill skill = getSkillById(id);
        skillRepository.delete(skill);
    }

    public List<Skill> getAllSkillsByUserId(String userId) {
        return skillRepository.getAllSkillsByUserId(userId);
    }
}
