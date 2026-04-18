package com.group.InternMap.Student;

import com.group.InternMap.Application.Application;
import com.group.InternMap.Application.ApplicationRepo;
import com.group.InternMap.cv.CV;
import com.group.InternMap.cv.CVRepo;
import com.group.InternMap.DTO.ApplicationAndCVDTO;
import com.group.InternMap.Job.JobPosting;
import com.group.InternMap.Job.JobPostingService;
import com.group.InternMap.User.UserRole;
import com.group.InternMap.User.UserService;
import com.group.InternMap.Notification.NotificationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/student")
public class RestStudentController {

    AuthenticationManager authenticationManager;

    private final ApplicationRepo applicationRepo;
    JobPostingService jobPostingService;
    UserService userService;
    CVRepo cvRepo;
    StudentRepo studentRepo;
    NotificationService notificationService;

    @Autowired
    public RestStudentController(JobPostingService jobPostingService, UserService userService, CVRepo cvRepo, StudentRepo studentRepo, ApplicationRepo applicationRepo, NotificationService notificationService, AuthenticationManager authenticationManager) {
        this.jobPostingService = jobPostingService;
        this.userService = userService;
        this.cvRepo = cvRepo;
        this.studentRepo = studentRepo;
        this.applicationRepo = applicationRepo;
        this.notificationService = notificationService;
        this.authenticationManager = authenticationManager;

    }

    @PostMapping("/register")
    public void registerStudent(HttpServletRequest request,@RequestBody Student user) throws ServletException, IllegalArgumentException {
        user.setRole(UserRole.STUDENT);
        userService.register(user, request);
    }

    @PostMapping("/application/save")
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

    @GetMapping("/student/applications")
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public List<Application> getStudentApplications(Principal principal, Authentication authentication) {

        if (authentication != null && authentication.getAuthorities().toString().equals("[ROLE_" + UserRole.STUDENT + "]")) {
            return applicationRepo.findByStudentEmail((userService.searchByEmail(principal.getName()).get()).getEmail());
        } else {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "User must be of role STUDENT to proceed");
        }
    }

}
