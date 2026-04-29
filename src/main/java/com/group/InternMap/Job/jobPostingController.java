package com.group.InternMap.Job;

import com.group.InternMap.Application.Application;
import com.group.InternMap.Application.ApplicationRepo;
import com.group.InternMap.Company.CompanyService;
import com.group.InternMap.DTO.JobPostingFactory;
import com.group.InternMap.Recruiter.Recruiter;
import com.group.InternMap.Recruiter.RecruiterController;
import com.group.InternMap.User.UserRole;
import com.group.InternMap.User.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/jobposting")
public class jobPostingController {

    CompanyService companyService;
    UserService userService;
    JobPostingService jobPostingService;
    ApplicationRepo applicationRepo;

    Logger logger = LoggerFactory.getLogger(RecruiterController.class);

    jobPostingController(CompanyService companyService, UserService userService, JobPostingService jobPostingService) {
        this.companyService = companyService;
        this.userService = userService;
        this.jobPostingService = jobPostingService;
    }

    @PostMapping("/new")
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public void AddJobPosting(@RequestBody JobPostingFactory jobPostingFactory, Principal principal, Authentication authentication) {

        if (authentication != null && authentication.getAuthorities().toString().equals("[ROLE_" + UserRole.RECRUITER + "]")) {
            jobPostingFactory.setCompany(companyService.findByName(jobPostingFactory.getCompany().getName()));

            jobPostingFactory.getJobPosting().setCompany(companyService.findByName(jobPostingFactory.getCompany().getName()));
            jobPostingFactory.getJobPosting().setRecruiter((Recruiter) userService.searchByEmail(principal.getName()).get());
            switch (jobPostingFactory.getJobType()) {
                case "FullTime" -> jobPostingService.save(jobPostingFactory.toInternship());
                case "FreelanceProject" -> jobPostingService.save(jobPostingFactory.toFullTime());
                case "Internship" -> jobPostingService.save(jobPostingFactory.toFreelanceProject());
            }
        } else {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "User must be of role RECRUITER to proceed");
        }
    }

    @GetMapping("/jobform")
    public List<JobPosting> writing()  {
        return jobPostingService.getAllJobPostings();

    }

    @PostMapping("/jobform/Create")
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public ResponseEntity<String> createJobPosting(@RequestBody JobPostingFactory dto, Principal principal) {

        dto.setCompany(companyService.findByName(dto.getCompany().getName()));
        dto.getJobPosting().setCompany(companyService.findByName(dto.getCompany().getName()));
        dto.getJobPosting().setRecruiter((Recruiter) userService.searchByEmail(principal.getName()).get());
//
        switch (dto.getJobType()) {
            case "FullTime"         -> jobPostingService.save(dto.toFullTime());
            case "FreelanceProject" -> jobPostingService.save(dto.toFreelanceProject());
            case "Internship"       -> jobPostingService.save(dto.toInternship());
        }

        return ResponseEntity.ok("everything is fine");
    }

    @GetMapping("/{jobId}/applications")
    public List<Application> viewApplications(@PathVariable long jobId, Authentication authentication) {

        if (authentication != null && authentication.getAuthorities().toString().equals("[ROLE_" + UserRole.STUDENT + "]")) {
            JobPosting job = jobPostingService.findJobPostingByID(jobId);
            if (job == null) {
                logger.warn("Job with ID {} not found", jobId);
                throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Job not found");
            }

            List<Application> apps = applicationRepo.findByJobPosting(job);

            logger.info("Fetched applications count: {}", (apps == null ? 0 : apps.size()));
            if (apps != null) {
                for (Application a : apps) {
                    logger.info("Application ID: {}, Job Posting ID: {}", a.getApplicationID(), (a.getJobPosting() == null ? "null" : a.getJobPosting().getId()));
                }
            }

            return apps;
        } else {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "User must be of role RECRUITER to proceed");
        }
    }

    @GetMapping("/jobpostings")
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public List<JobPosting> getRecruiterJobPostings(Principal principal, Authentication authentication) {

        if (authentication != null && authentication.getAuthorities().toString().equals("[ROLE_" + UserRole.STUDENT + "]")) {
            return jobPostingService.getJobPostingsByRecruiterId((userService.searchByEmail(principal.getName()).get()).getId());
        } else {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "User must be of role RECRUITER to proceed");
        }
    }

}
