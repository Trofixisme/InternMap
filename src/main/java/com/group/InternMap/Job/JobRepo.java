package com.group.InternMap.Job;

import com.group.InternMap.Student.Student;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobRepo extends JpaRepository<JobPosting, Long> {
    List<JobPosting> findJobPostingByName(String jobName);

    List<JobPosting> findJobPostingById(Long id);
}