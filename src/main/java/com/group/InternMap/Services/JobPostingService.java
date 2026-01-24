package com.group.InternMap.Services;

import com.group.InternMap.Model.Job.JobPosting;
import com.group.InternMap.Repo.BaseRepository;
import com.group.InternMap.Repo.RepositoryAccessors;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;
import static com.group.InternMap.Repo.RepositoryAccessors.allJobPostings;
import static com.group.InternMap.Services.FilePaths.jobPostingPath;

@Service
public class JobPostingService {

    private final ArrayList<JobPosting> jobRepo = RepositoryAccessors.allJobPostings;
    public List<JobPosting> searchJobPostings(String searchQuery) throws Exception {
        return jobRepo.stream().filter(job -> {
            boolean matches = false;

            if (searchQuery != null && !searchQuery.isBlank()) {
                matches |= job.getJobName().toLowerCase(Locale.ROOT).contains(searchQuery.toLowerCase(Locale.ROOT));
                matches |= job.getCompanyName().toLowerCase(Locale.ROOT).contains(searchQuery.toLowerCase(Locale.ROOT));
            }

            return matches;
        }).toList();
    }

    public List<JobPosting> getAllJobPostings() throws Exception {
      return  RepositoryAccessors.allJobPostings;
    }
    public JobPosting findByID(UUID jopPostingId) {
        System.out.println(jopPostingId);

         return allJobPostings.stream()
//                 .filter(j -> j.getJobPostingUUID().equals(jopPostingId))
                 .findFirst().orElse(null);
    }

public List<JobPosting> getJobPostingsByRecruiterId(UUID recruiterId) throws Exception {

    System.out.println("Looking for jobs for recruiter: " + recruiterId);

    return allJobPostings.stream()
            .peek(job -> System.out.println("Job Recruiter: " +
                    (job.getRecruiter() == null ? "null" : job.getRecruiter().getUserID())))
            .filter(job -> job.getRecruiter() != null)
            .filter(job -> recruiterId.equals(job.getRecruiter().getUserID()))
            .collect(Collectors.toList());
}






}
