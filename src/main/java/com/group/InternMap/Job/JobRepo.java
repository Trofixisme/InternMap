package com.group.InternMap.Job;
import com.group.InternMap.Application.Application;
import jakarta.persistence.metamodel.SingularAttribute;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface JobRepo extends JpaRepository<JobPosting, Long> {
    List<JobPosting> findJobPostingByJobName(String jobName);

    @Override
    Optional<JobPosting> findById(Long aLong);

    JobPosting findJobPostingById(Long id);

    List<JobPosting> findJobPostingByRecruiterId(Long recruiterId);



//    @Query("SELECT j FROM JobPosting j WHERE " +
//            "LOWER(j.jobName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
//            "LOWER(j.jobDescription) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
//            "LOWER(j.company.name) LIKE LOWER(CONCAT('%', :query, '%'))")
//    List<JobPosting> searchJobs(@Param("query") String query);
}