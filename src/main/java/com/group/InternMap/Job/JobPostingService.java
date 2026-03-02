package com.group.InternMap.Job;

import com.group.InternMap.Deprecated.Repository.RepositoryAccessors;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;
import static com.group.InternMap.Deprecated.Repository.RepositoryAccessors.allJobPostings;

@Service
public class JobPostingService {

    private final ArrayList<JobPosting> jobRepo = RepositoryAccessors.allJobPostings;
    public List<JobPosting> searchJobPostings(String searchQuery) throws Exception {
        return jobRepo.stream().filter(job -> {
            boolean matches = false;

            if (searchQuery != null && !searchQuery.isBlank()) {
                matches |= job.getJobName().toLowerCase(Locale.ROOT).contains(searchQuery.toLowerCase(Locale.ROOT));
                matches |= job.getCompany().getName().toLowerCase(Locale.ROOT).contains(searchQuery.toLowerCase(Locale.ROOT));
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

    public List<JobPosting> getJobPostingsByRecruiterId(long recruiterId) throws Exception {

        System.out.println("Looking for jobs for recruiter: " + recruiterId);

        return allJobPostings.stream()
                .peek(job -> System.out.println("Job Recruiter: " +
                        (job.getRecruiter() == null ? "null" : job.getRecruiter().getId())))
                .filter(job -> job.getRecruiter() != null)
                .filter(job -> recruiterId == job.getRecruiter().getId())
                .collect(Collectors.toList());
    }
}
