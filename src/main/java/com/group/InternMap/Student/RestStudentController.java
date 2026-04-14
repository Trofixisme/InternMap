package com.group.InternMap.Student;

import com.group.InternMap.Application.Application;
import com.group.InternMap.Application.ApplicationRepo;
import com.group.InternMap.Application.CV;
import com.group.InternMap.Application.CVRepo;
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

@RequestMapping("/REST")
@RestController
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

    @PostMapping("/student/register")
    public void registerStudent(HttpServletRequest request, @ModelAttribute("user") Student user) throws ServletException, IllegalArgumentException {
        user.setRole(UserRole.STUDENT);
        userService.register(user, request);
    }

    @GetMapping("/cv")
    public CV cv(Principal principal, Authentication authentication) throws HttpClientErrorException.Unauthorized {

        if (authentication != null && authentication.getAuthorities().toString().equals("[ROLE_" + UserRole.STUDENT + "]")) {
            Student student = studentRepo.findByEmail(principal.getName());

            return student.getCv();
        } else {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "User must be of " + UserRole.STUDENT  +" to proceed");
        }
    }

    @PostMapping("/cv/save")
    public void saveCV(@ModelAttribute("cv") CV cv, Principal principal, Authentication authentication) {

        if (authentication != null && authentication.getAuthorities().toString().equals("[ROLE_" + UserRole.STUDENT + "]")) {
            Student student = studentRepo.findByEmail(principal.getName());

            if (student.getCv() != null) {
                System.out.println(student.getCv());
                CV existingCV = student.getCv();
                existingCV.setDescription(cv.getDescription());
                existingCV.setPastExperiences(cv.getPastExperiences());
                existingCV.setProjects(cv.getProjects());
                student.setCv(existingCV);
                System.out.println(existingCV);
                cvRepo.save(existingCV);
                studentRepo.save(student);
            } else {
                student.setCv(cv);
                cvRepo.save(cv);
                studentRepo.save(student);
            }

        } else {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "User must be of role STUDENT to proceed");
        }
    }

    @PostMapping("/application/save")
    public void saveApplication(@RequestParam("jobId") long jobId, @ModelAttribute ApplicationAndCVDTO applicationandCVDTO, Authentication authentication, Principal principal, RedirectAttributes redirectAttributes) {
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
            notificationService.sendToUser(recruiterEmail, "An application is submitted");
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
