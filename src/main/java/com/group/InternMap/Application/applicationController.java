package com.group.InternMap.Application;

import com.group.InternMap.DTO.ApplicationAndCVDTO;
import com.group.InternMap.Job.JobPosting;
import com.group.InternMap.Job.JobPostingService;
import com.group.InternMap.Notification.NotificationService;
import com.group.InternMap.Recruiter.RecruiterController;
import com.group.InternMap.Student.Student;
import com.group.InternMap.Student.StudentRepo;
import com.group.InternMap.User.UserRole;
import com.group.InternMap.User.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
@RestController
@RequestMapping("/api/application")
public class applicationController {
    StudentRepo studentRepo;
    JobPostingService jobPostingService;
    ApplicationRepo applicationRepo;
    NotificationService notificationService;
    UserService userService;
    ApplicationService applicationService;
    Logger logger = LoggerFactory.getLogger(RecruiterController.class);
    @Autowired
    applicationController(StudentRepo studentRepo,
    JobPostingService jobPostingService,
    ApplicationRepo applicationRep,
    NotificationService notificationService,
    UserService userService,ApplicationService applicationService){
        this.applicationRepo=applicationRep;
        this.studentRepo=studentRepo;
        this.jobPostingService=jobPostingService;
        this.notificationService=notificationService;
        this.userService=userService;
        this.applicationService=applicationService;

    }
    @PostMapping("/new")
    public void saveApplication(@RequestParam("jobId") long jobId, @RequestBody ApplicationAndCVDTO applicationandCVDTO, Authentication authentication, Principal principal, RedirectAttributes redirectAttributes) {
        if (authentication != null && authentication.getAuthorities().toString().equals("[ROLE_" + UserRole.STUDENT + "]")) {
            Student user = studentRepo.findByEmail(principal.getName());
            if (user.getCv() == null) {
                throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "User does not have a CV");
            }

            JobPosting jobPosting = jobPostingService.findJobPostingByID(jobId);
            if (jobPosting == null) {
                throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Job posting not found");
            }
            Application application = applicationandCVDTO.getApplication();
            applicationandCVDTO.setStudent(user);
            application.setStudent(user);
            jobPosting.addApplication(application);
            applicationRepo.save(application);
            redirectAttributes.addFlashAttribute("message", "Application saved successfully");
            String recruiterEmail = jobPosting.getRecruiterEmail();
            notificationService.sendToUser(recruiterEmail, authentication.getName() + " has applied to " + applicationandCVDTO.getApplication().getJobPosting().getJobName());
            System.out.println("Notification triggered!");
        } else {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "User must be of role STUDENT to proceed");
        }
    }

    @GetMapping("/student")
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public List<Application> getStudentApplications(Principal principal, Authentication authentication) {

        if (authentication != null && authentication.getAuthorities().toString().equals("[ROLE_" + UserRole.STUDENT + "]")) {
            return applicationRepo.findByStudentEmail((userService.searchByEmail(principal.getName()).get()).getEmail());
        } else {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "User must be of role STUDENT to proceed");
        }
    }
    @PostMapping("/search")
    public List<Application> searchApplication( @RequestParam("searchQuery") String searchQuery) {

        return applicationService.searchApplication(searchQuery);
    }
    @GetMapping("/jobpostings/{jobId}/applications")
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
}
