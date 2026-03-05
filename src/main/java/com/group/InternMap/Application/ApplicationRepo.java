package com.group.InternMap.Application;

import com.group.InternMap.Recruiter.Recruiter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepo extends JpaRepository<Application, Long> {
    List <Application> findByEmail(String email);
}