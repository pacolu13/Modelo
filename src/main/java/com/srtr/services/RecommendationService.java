package com.srtr.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.srtr.models.Connection;
import com.srtr.models.Job;
import com.srtr.models.Skill;
import com.srtr.models.User;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RecommendationService {

    private final UserService userService;
    private final ConnectionService connectionService;
    private final JobService jobService;
    private final SkillService skillService;

    public List<Job> recommendJobsForUser(String userId) {

        List<Job> allJobs = jobService.GetJobsRequireAtLeastOneSkill(userId);
        List<Skill> userSkills = skillService.getAllSkillsByUserId(userId);

        Set<Skill> userSkillsSet = new HashSet<>(userSkills);
        Map<Job, Double> jobScores = new HashMap<>();

        for (Job job : allJobs) {

            long matchCount = job.getRequiredSkills().stream().filter(userSkillsSet::contains).count();
            int totalRequired = job.getRequiredSkills().size();
            double score = (double) matchCount / totalRequired;

            jobScores.put(job, score);
        }

        return jobScores.entrySet().stream()
                .sorted((a, b) -> Double.compare(b.getValue(), a.getValue()))
                .limit(5)
                .map(entry -> entry.getKey())
                .toList();
    }

    public List<User> recommendedUsersForUser(String userId) {
        List<Skill> userSkills = skillService.getAllSkillsByUserId(userId);
        Set<Skill> userSkillsSet = new HashSet<>(userSkills);
        Map<User, Double> userRecommended = new HashMap<>();

        List<User> users = userService.getAllUsers()
                .stream()
                .filter(user -> !user.getId().equals(userId))
                .filter(user -> user.getSkills().stream()
                        .anyMatch(userSkillsSet::contains))
                .toList();

        for (User user : users) {
            long matchCount = user.getSkills().stream().filter(userSkillsSet::contains).count();
            int totalUserSkills = user.getSkills().size();
            double score = (double) matchCount / totalUserSkills;

            userRecommended.put(user, score);
        }

        return userRecommended.entrySet()
                .stream()
                .sorted((a, b) -> Double.compare(b.getValue(), a.getValue()))
                .limit(5).map(entry -> entry.getKey()).toList();

    }

    public List<Skill> recommendedSkillsForUser(String userId) {
        Map<Skill, Integer> skillRecommended = new HashMap<>();

        User fromUser = userService.getUserById(userId);
        List<Skill> fromUserSkills = skillService.getAllSkillsByUserId(userId);

        // Añadimos las skills del user al set

        List<String> connections = fromUser.getConnections();

        List<Connection> userConnectionsAccepted = connectionService.getAllConnectionsById(connections)
                .stream().filter(cs -> cs.getStatus().equals("accepted")).toList();

        for (Connection connection : userConnectionsAccepted) {
            User toUser = userService.getUserById(connection.getToUser());
            List<Skill> toUserSkills = skillService.getAllSkillsByUserId(toUser.getId());

            // por cada aparicion distinta del set de skills, añadimos la skill y si ya
            // existe aumentamos
            // la aparicion en 1. por ultimo filtramos las de mayor aparicion
        }
    }
}
