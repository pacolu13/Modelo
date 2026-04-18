package com.srtr.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.*;

import org.springframework.stereotype.Service;

import com.srtr.models.Post;
import com.srtr.models.PostSearchData;
import com.srtr.models.Skill;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class PostAnalysisService {

    private final SkillService skillService;

    public PostSearchData analyzePost(Post post) {

        String text = normalizeText(post.getTitle() + " " + post.getDescription());

        List<Skill> allSkills = skillService.getAllSkills();

        Map<Skill, Integer> skillsDetected = detectSkills(text, allSkills);
        int yearsExp = detectYearsOfExperience(text);

        PostSearchData data = new PostSearchData();
        data.setSkillsData(skillsDetected);
        data.setAverageExp(yearsExp);

        return data;
    }

    /*
     * 🔹 Normaliza texto
     */
    private String normalizeText(String text) {
        return text.toLowerCase().replaceAll("[^a-z0-9\\s]", "");
    }

    /*
     * Detecta skills y cuenta frecuencia
     */
    private Map<Skill, Integer> detectSkills(String text, List<Skill> skills) {

        Map<Skill, Integer> skillCount = new HashMap<>();

        for (Skill skill : skills) {

            String skillName = skill.getName().toLowerCase();

            Pattern pattern = Pattern.compile("\\b" + Pattern.quote(skillName) + "\\b");
            Matcher matcher = pattern.matcher(text);

            int count = 0;

            while (matcher.find()) {
                count++;
            }

            if (count > 0) {
                skillCount.put(skill, count);
            }
        }

        return skillCount;
    }

    /*
     * Detecta años de experiencia
     */
    private int detectYearsOfExperience(String text) {

        Pattern pattern = Pattern.compile("(\\+?\\d+)\\s*(años|years)");
        Matcher matcher = pattern.matcher(text);

        int maxYears = 0;

        while (matcher.find()) {
            String number = matcher.group(1).replace("+", "");
            int years = Integer.parseInt(number);
            maxYears = Math.max(maxYears, years);
        }

        return maxYears;
    }
}
