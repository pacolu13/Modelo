package com.srtr.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.srtr.models.Connection;
import com.srtr.models.Job;
import com.srtr.models.Post;
import com.srtr.models.PostSearchData;
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
    private final PostAnalysisService postAnalysisService;

    // Variables constantes
    private static final double REQUIRED_WEIGHT = 0.7;
    private static final double DESIRABLE_WEIGHT = 0.3;

    private static final double SENIORITY_SCORE = 1.0;

    private static final double EXP_SCORE = 1.0;

    private static final double REQUIRED_SKILL_THRESHOLD = 0.5;
    private static final double REQUIRED_SKILL_PENALTY = 0.15;

    public List<Job> recommendJobsForUser(String userId) {

        User user = userService.getUserById(userId);
        List<Job> allJobs = jobService.GetJobsRequireAtLeastOneSkill(userId);
        Map<Job, Double> jobScores = getScore(user, allJobs);

        return jobScores.entrySet().stream()
                .sorted((a, b) -> Double.compare(b.getValue(), a.getValue()))
                .limit(5)
                .map(entry -> entry.getKey())
                .toList();
    }

    private Map<Job, Double> getScore(User user, List<Job> jobs) {

        Map<Job, Double> jobScores = new HashMap<>();
        Set<Skill> skillsUser = user.getSkills().stream().collect(Collectors.toSet());

        for (Job job : jobs) {

            double score = 0.0;
            score = userSkillsMatchScore(skillsUser, job, score);
            score = userSeniorityScore(user, job, score);
            score = userExpScore(user, job, score);
            score = userPostScore(user, job, score);

            jobScores.put(job, score);
        }

        return jobScores;
    }

    /*
     * Metodo para analizar el match skill del user.
     */
    private double userSkillsMatchScore(Set<Skill> skillsUser, Job job, double score) {
        long matchRequired = job.getRequiredSkills().stream()
                .filter(skillsUser::contains)
                .count();

        int totalRequired = job.getRequiredSkills().size();

        double requiredRatio = totalRequired == 0 ? 0 : ((double) matchRequired / totalRequired);

        score += requiredRatio * REQUIRED_WEIGHT;

        if (requiredRatio < REQUIRED_SKILL_THRESHOLD) {
            score -= REQUIRED_SKILL_PENALTY;
        }

        long matchDesirable = job.getDesirableSkills().stream()
                .filter(skillsUser::contains)
                .count();

        int totalDesirable = job.getDesirableSkills().size();

        double desirableRatio = totalDesirable == 0 ? 0 : ((double) matchDesirable / totalDesirable);

        score += desirableRatio * DESIRABLE_WEIGHT;
        return score;
    }

    /*
     * Metodo para analizar la experiencia del user.
     */
    private double userExpScore(User user, Job job, double score) {
        if (job.getRequiredExp() <= user.getExperienceYears()) {
            score += EXP_SCORE;
        } else if (job.getDesirableExp() <= user.getExperienceYears()) {
            score += EXP_SCORE / 2;
        }
        return score;
    }

    /*
     * Metodo para analizar el seniority del user.
     */
    private double userSeniorityScore(User user, Job job, double score) {
        if (job.getRequiredSeniority().contains(user.getSeniority())) {
            score += SENIORITY_SCORE;
        } else if (job.getDesirableSeniority().contains(user.getSeniority())) {
            score += SENIORITY_SCORE / 2;
        }
        return score;
    }

    private double userPostScore(User user, Job job, double score) {
        List<Post> posts = user.getPosts();
        for (Post post : posts) {
            PostSearchData dataPost = postAnalysisService.analyzePost(post);
            Set<Skill> userSkillsPost = dataPost.getSkillsMatcher().stream().collect(Collectors.toSet());
            score = userSkillsMatchScore(userSkillsPost, job, score);
        }
        return score;
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
        Set<Skill> fromUserSkillsSet = new HashSet<>();

        for (Skill skill : fromUserSkills) {
            fromUserSkillsSet.add(skill);
        }

        List<String> connections = fromUser.getConnections();

        List<Connection> userConnectionsAccepted = connectionService.getAllConnectionsById(connections)
                .stream().filter(cs -> cs.getStatus().equals("accepted")).toList();

        for (Connection connection : userConnectionsAccepted) {
            User toUser = userService.getUserById(connection.getToUser());
            List<Skill> toUserSkills = skillService.getAllSkillsByUserId(toUser.getId());

            for (Skill skill : toUserSkills) {
                if (!fromUserSkillsSet.contains(skill)) {
                    if (skillRecommended.containsKey(skill)) {
                        Integer cant = skillRecommended.get(skill);
                        skillRecommended.replace(skill, cant + 1);
                    } else {
                        skillRecommended.put(skill, 1);
                    }
                }
            }
        }
        return skillRecommended.entrySet()
                .stream()
                .sorted((a, b) -> Double.compare(b.getValue(), a.getValue()))
                .limit(5).map(entry -> entry.getKey()).toList();
    }
}
