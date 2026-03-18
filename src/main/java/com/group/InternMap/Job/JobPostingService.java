package com.group.InternMap.Job;
import com.group.InternMap.Application.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;


@Service
public class JobPostingService {

    JobRepo jobRepo;

    @Autowired
    public JobPostingService(JobRepo jobRepo) {
        this.jobRepo = jobRepo;
    }

    public List<JobPosting> getAllJobPostings()  {
        return  jobRepo.findAll();
    }

    public List<JobPosting> getJobPostingsByRecruiterId(long recruiterId)  {
        return jobRepo.findJobPostingByRecruiterId(recruiterId);
    }

    public List<JobPosting> getAllJobPostingsName(String name) {
        return jobRepo.findJobPostingByJobName(name);
    }

    public JobPosting findJobPostingByID(long appId) {
        return  jobRepo.findJobPostingById(appId);
    }

    public JobPosting createJobPosting(JobPosting jobPosting) {
        return jobRepo.save(jobPosting);
    }

    public void deleteJobPosting(JobPosting jobPosting){
         jobRepo.delete(jobPosting);
    }

    public List<JobPosting> findJobPostingByName(String name) {
        return jobRepo.findJobPostingByJobName(name);
    }
//    JobPosting job =  jobRepo.getById(id);

}
