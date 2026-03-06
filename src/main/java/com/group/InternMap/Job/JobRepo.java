package com.group.InternMap.Job;

import com.group.InternMap.Application.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepo extends JpaRepository<JobPosting, Long> {
    List<JobPosting> findJobPostingByJobName(String jobName);

    JobPosting findJobPostingById(Long id);

    List<JobPosting> findJobPostingByRecruiterId(Long recruiterId);


//    @Query("SELECT j FROM JobPosting j WHERE " +
//            "LOWER(j.jobName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
//            "LOWER(j.jobDescription) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
//            "LOWER(j.company.name) LIKE LOWER(CONCAT('%', :query, '%'))")
//    List<JobPosting> searchJobs(@Param("query") String query);
}