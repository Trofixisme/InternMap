package com.group.InternMap.Application;

import com.group.InternMap.Job.JobPosting;
import com.group.InternMap.Job.JobRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ApplicationService {

    ApplicationRepo applicationRepo;
    JobRepo jobRepo;

    @Autowired
    public ApplicationService(ApplicationRepo applicationRepo, JobRepo jobRepo) {
        this.applicationRepo = applicationRepo;
        this.jobRepo = jobRepo;
    }

    public List<Application> searchApplication(String searchQuery) {
        return applicationRepo.searchApplications(searchQuery);
    }

    public List<Application> getApplicationsByJobPostingId(Long jobPostingId)throws IllegalArgumentException{
        if (jobPostingId == null) {
            throw new IllegalArgumentException("Job posting cannot be null");
        }
        JobPosting posting = jobRepo.findJobPostingById(jobPostingId);
        return posting.getApplications();
    }

    public List<Application> viewAllApplications() {
        return applicationRepo.findAll();
    }

    public void deleteApplication(Application application) throws Exception {
        List<Application> applicationList = applicationRepo.findAll();
        if (applicationList.contains(application)) {
            applicationRepo.delete(application);
        }
        else {
            throw new Exception("There is no application with this specification");
        }

    }



}
