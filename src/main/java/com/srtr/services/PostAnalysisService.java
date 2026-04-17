package com.srtr.services;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

import org.springframework.stereotype.Service;

import com.srtr.models.Post;
import com.srtr.models.Skill;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class PostAnalysisService {

    private final SkillService skillService;

    private Data titleAnalysis(Post post) {
        Data dataPost = new Data();
        List<Skill> skillsMatched = new ArrayList<>();
        String postTitle = post.getTitle().toLowerCase();
        String postDescription = post.getDescription().toLowerCase();

        List<Skill> skills = skillService.getAllSkills();
        for (Skill skill : skills) {
            if (skill.getName().toLowerCase().contains(postTitle)
                    || skill.getName().toLowerCase().contains(postDescription)
                    || skill.getCategory().toLowerCase().contains(postTitle)
                    || skill.getCategory().toLowerCase().contains(postDescription)) {
                skillsMatched.add(skill);
            }
        }

        /*
         * Expresiones reguales : Aprender
         * \\d+ -> Uno o mas
         * \\s* -> Espacios opcionales
         * \\(años|years) -> palabras claves
         */
        String text = post.getBody().toLowerCase();

        Pattern pattern = Pattern.compile("(\\d+)\\s*(años|years)");
        Matcher matcher = pattern.matcher(text);

        int totalExp = 0;

        while (matcher.find()) {
            String number = matcher.group(1);
            int years = Integer.parseInt(number);
            totalExp = Math.max(totalExp, years); // Me quedo con el mayor
        }
        return dataPost;
    }

    public class Data {
        private List<Skill> skillsData;
        private Integer averageExp;
    }

}
