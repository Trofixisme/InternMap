package com.group.InternMap.Application;

import com.group.InternMap.Recruiter.Recruiter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepo extends JpaRepository<Application, Long> {

}