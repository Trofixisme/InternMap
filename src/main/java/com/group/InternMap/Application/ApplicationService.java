package com.group.InternMap.Application;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {
    ApplicationRepo applicationRepo;
    public List<Application> findApplicationByEmail(String email) throws Exception{
        if (email == null || email.isBlank()) return null;
        return applicationRepo.findByEmail(email);
    }
}
