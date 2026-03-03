package com.group.InternMap.Recruiter;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruiterRepo extends JpaRepository<Recruiter, Long> {
       Recruiter findRecruiterById(Long id);
       List<Recruiter> findRecruiterByName(String name);
}
