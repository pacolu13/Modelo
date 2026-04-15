package com.srtr.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.srtr.models.Job;
import com.srtr.models.Skill;
import com.srtr.models.User;
import com.srtr.repositories.JobRepository;
import com.srtr.repositories.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class JobService {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public Job getJobById(String id) {
        return jobRepository.findById(id).orElse(null);
    }

    public Job updateJob(String id, Job updatedJob) {
        return jobRepository.findById(id).map(job -> {
            job.setTitle(updatedJob.getTitle());
            job.setCompany(updatedJob.getCompany());
            job.setRequiredSkills(updatedJob.getRequiredSkills());
            job.setAplicants(updatedJob.getAplicants());
            return jobRepository.save(job);
        }).orElse(null);

    }

    public void deleteJob(String id) {
        jobRepository.deleteById(id);
    }

    public List<Job> GetJobsRequireAtLeastOneSkill(String userId) {
        User user = userRepository.findById(userId).orElse(null);
        List<Job> jobs = jobRepository.findAll();
        List<String> userSkills = user.getSkills();

        List<Job> recommendedJobs = jobs.stream()
                .filter(job -> job.getRequiredSkills()
                .stream()
                .anyMatch(skill ->userSkills.contains(skill)))
                .toList();
        return recommendedJobs;
    }
}
