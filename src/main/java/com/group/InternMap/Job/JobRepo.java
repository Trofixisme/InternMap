package com.group.InternMap.Job;

import com.group.InternMap.Student.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepo extends JpaRepository<JobPosting, Long> {}