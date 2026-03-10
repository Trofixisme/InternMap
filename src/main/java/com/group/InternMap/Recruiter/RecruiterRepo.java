package com.group.InternMap.Recruiter;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecruiterRepo extends JpaRepository<Recruiter, Long> {
       Recruiter findRecruiterById(Long id);

       List<Recruiter> findRecruiterByfName(String fName);

       List<Recruiter> findRecruiterBylName(String lName);
//       @Query("SELECT r FROM Recruiter r LEFT JOIN FETCH r.companies WHERE r.id = :id")
//       Recruiter findRecruiterWithCompanies(@Param("id") Long id);
@Query("SELECT r FROM Recruiter r LEFT JOIN FETCH r.companies WHERE r.id = :id")
Optional<Recruiter> findRecruiterWithCompanies(@Param("id") Long id);





}
