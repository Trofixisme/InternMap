package com.group.InternMap.Application;

import com.group.InternMap.Job.JobPosting;
import com.group.InternMap.Job.JobRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {

    ApplicationRepo applicationRepo;
    JobRepo jobRepo;

    public ApplicationService(ApplicationRepo applicationRepo) {
        this.applicationRepo = applicationRepo;
    }

    public List<Application> searchApplication(String studentEmail) throws Exception {
        if (studentEmail == null || studentEmail.isBlank()) return null;
        return applicationRepo.findByEmail(studentEmail);
    }

    public List<Application> getApplicationsByJobPostingId(Long jobPostingId) {
        if (jobPostingId == null) {
            throw new IllegalArgumentException("Job posting cannot be null");
        }
        JobPosting joposting = jobRepo.findJobPostingById(jobPostingId);
        List<Application> applications = joposting.getApplication();

        return applications;
    }

        public List<Application> viewAllApplications() {
        return applicationRepo.findAll();
    }



}
