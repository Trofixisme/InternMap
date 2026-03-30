package com.group.InternMap.Job;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface JobRepo extends JpaRepository<JobPosting, Long> {
    List<JobPosting> findJobPostingByJobName(String jobName);

    JobPosting findJobPostingById(Long id);

    List<JobPosting> findJobPostingByRecruiterId(Long recruiterId);

    @Query("SELECT j FROM JobPosting j JOIN  j.company c WHERE " +
            "LOWER(j.jobName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(c.name) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<JobPosting> searchJobs(@Param("query") String query);

}