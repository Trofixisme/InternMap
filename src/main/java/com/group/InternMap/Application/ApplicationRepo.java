package com.group.InternMap.Application;

import com.group.InternMap.Job.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApplicationRepo extends JpaRepository<Application, Long> {
    List<Application> findByEmail(String email);

    @Query(value = "SELECT * FROM application WHERE job_posting_id = ?1", nativeQuery = true)
    List<Application> findByJobPostingId(Long jobPostingId);


}