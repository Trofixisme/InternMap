package com.group.InternMap.Application;

import com.group.InternMap.Job.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepo extends JpaRepository<Application, Long> {
    List<Application> findByEmail(String email);

    // Correct Spring Data JPA derived query for the JobPosting relationship's id
    List<Application> findByJobPosting_Id(Long jobPostingId);

    // Alternative: query by JobPosting entity reference
    List<Application> findByJobPosting(JobPosting jobPosting);

}