package com.group.InternMap.Job;
import com.group.InternMap.Deprecated.Repository.RepositoryAccessors;
import org.springframework.stereotype.Service;
import java.util.*;


@Service
public class JobPostingService {
    JobRepo jobRepo;

    public List<JobPosting> getAllJobPostings()  {
        return  RepositoryAccessors.allJobPostings;
    }

    public List<JobPosting> getJobPostingsByRecruiterId(long recruiterId)  {
        List<JobPosting> jobPostings = jobRepo.findJobPostingByRecruiterId(recruiterId);
        return jobPostings;
    }

    public List<JobPosting> getAllJobPostingsName(String name) {
        return jobRepo.findJobPostingByJobName(name);
    }

    public JobPosting findJobpostingByID(long appId) {
        return  jobRepo.findJobPostingById(appId);
    }

}
