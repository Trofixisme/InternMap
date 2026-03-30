package com.group.InternMap.Application;

import com.group.InternMap.Job.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;


public interface ApplicationRepo extends JpaRepository<Application, Long> {
    // Alternative: query by JobPosting entity reference
    List<Application> findByJobPosting(JobPosting jobPosting);

    @Query("SELECT a FROM Application a JOIN a.jobPosting j WHERE " +
            "LOWER(a.email) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(a.fName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(a.lName) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Application> searchApplications(@Param("query") String query);

}