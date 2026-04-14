package com.group.InternMap.Recruiter;

import com.group.InternMap.Application.Application;
import com.group.InternMap.Application.ApplicationRepo;
import com.group.InternMap.Application.CV;
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
@RequestMapping("/REST")
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

    @PostMapping("/recruiter/company/register")
    public void RegisterCompany(@ModelAttribute("company") Company company) {

        companyService.save(company);
    }

    @PostMapping("/recruiter/register")
    public void registerRecruiter(HttpServletRequest request, @ModelAttribute("form") RecruiterRegistrationDTO recruiterRegistrationDTO) throws ServletException, DataIntegrityViolationException {
        recruiterService.registerRecruiter(recruiterRegistrationDTO, request);
    }

    @PostMapping("/JobPostingForm")
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public void AddJobPosting(@ModelAttribute JobPostingFactory jobPostingFactory, Principal principal, Authentication authentication) {

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

    @GetMapping("/recruiter/jobpostings")
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public List<JobPosting> getRecruiterJobPostings(Principal principal, Authentication authentication) {

        if (authentication != null && authentication.getAuthorities().toString().equals("[ROLE_" + UserRole.STUDENT + "]")) {
            return jobPostingService.getJobPostingsByRecruiterId((userService.searchByEmail(principal.getName()).get()).getId());
        } else {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "User must be of role RECRUITER to proceed");
        }
    }

    @GetMapping("/JobPostings/{jobId}/applications")
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

    @GetMapping("/cv/{email}")
    public CV viewCV(@PathVariable String email, Authentication authentication) {

        if (authentication != null && (authentication.getAuthorities().toString().equals("[ROLE_" + UserRole.STUDENT + "]") || authentication.getAuthorities().toString().equals("[ROLE_" + UserRole.ADMIN + "]"))) {
            Optional<Users> retrievedStudent = userService.searchByEmail(email);
            if (retrievedStudent.isEmpty()) {
                throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Student could not be found");
            }

            return ((Student) retrievedStudent.get()).getCv();

        } else {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "User must be of role RECRUITER or ADMIN to proceed");
        }
    }

}
