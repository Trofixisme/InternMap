package com.group.InternMap.Recruiter;

import com.group.InternMap.Application.Application;
import com.group.InternMap.Application.ApplicationRepo;
import com.group.InternMap.cv.CV;
import com.group.InternMap.Company.Company;
import com.group.InternMap.Company.CompanyService;
import com.group.InternMap.DTO.JobPostingFactory;
import com.group.InternMap.DTO.RecruiterRegistrationDTO;
import com.group.InternMap.Job.JobPosting;
import com.group.InternMap.Job.JobPostingService;
import com.group.InternMap.Student.Student;
import com.group.InternMap.User.UserRole;
import com.group.InternMap.User.UserService;
import com.group.InternMap.User.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/recruiter")
public class RestRecruiterController {

    CompanyService companyService;
    RecruiterService recruiterService;
    UserService userService;
    JobPostingService jobPostingService;
    ApplicationRepo applicationRepo;

    Logger logger = LoggerFactory.getLogger(RecruiterController.class);

    @Autowired
    public RestRecruiterController(RecruiterService recruiterService, CompanyService companyService, UserService userService, JobPostingService jobPostingService, ApplicationRepo appRepo) {
        this.recruiterService = recruiterService;
        this.userService = userService;
        this.companyService = companyService;
        this.jobPostingService = jobPostingService;
        this.applicationRepo = appRepo;
    }



    @PostMapping("/register")
    public void registerRecruiter(HttpServletRequest request, @RequestBody RecruiterRegistrationDTO recruiterRegistrationDTO) throws ServletException, DataIntegrityViolationException {
        recruiterService.registerRecruiter(recruiterRegistrationDTO, request);
    }








}
