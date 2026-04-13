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
import groovy.util.ResourceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.ui.Model;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
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
    public boolean registerStudent(HttpServletRequest request, @ModelAttribute("user") Student user) throws ServletException {
        user.setRole(UserRole.STUDENT);
        userService.register(user, request);
        return true;
    }

    @GetMapping("/cv")
    @PreAuthorize("hasRole('STUDENT')")
    public String cv(Model model, Principal principal) {
        Student student = studentRepo.findByEmail(principal.getName());

        if (student.getCv() != null) {
            model.addAttribute("cv", student.getCv());
        } else {
            model.addAttribute("cv", new CV());
        }

        return "CV";
    }

    @PostMapping("/cv/save")
    @PreAuthorize("hasRole('STUDENT')")
    public String saveCV(@ModelAttribute("cv") CV cv, Principal principal) {
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

        return "redirect:/profile";
    }

    @GetMapping("/applications/new")
    public String createNewApplication(@RequestParam("jobId") long jobPostingId, Model model, Principal principal, RedirectAttributes redirectAttributes) {
        if (principal == null) {
            return "redirect:/login";
        }

        JobPosting jobPosting = jobPostingService.findJobPostingByID(jobPostingId);
        if (jobPosting == null) {
            redirectAttributes.addFlashAttribute("error","Job posting not found");
            return "redirect:/jobPosting";
        }

        model.addAttribute("jobId", jobPostingId);
        model.addAttribute("applicationandCVDTO", new ApplicationAndCVDTO());
        model.addAttribute("jobPosting", jobPosting); // Add job details for displaying
        return "Application";
    }

    @PostMapping("/application/save")
    public String saveApplication(@RequestParam("jobId") long jobId, @ModelAttribute ApplicationAndCVDTO applicationandCVDTO, Model model, Principal principal, RedirectAttributes redirectAttributes)  {
        if (principal == null) {
            return "redirect:/login";
        }
        Student user = studentRepo.findByEmail(principal.getName());
        if (user.getCv() == null) {
            redirectAttributes.addFlashAttribute("error","CV not found");
            return "redirect:/cv";
        }
        try {
            JobPosting jobPosting = jobPostingService.findJobPostingByID(jobId);
            if (jobPosting == null) {
                redirectAttributes.addFlashAttribute("error","Job posting not found");
                return "redirect:/JobPostings";
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
            return "redirect:/JobPostings";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            model.addAttribute("error", "Error saving application: " + e.getMessage());
            return "redirect:/applications/new?jobId=" + jobId;
        }

    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/student/applications")
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public String getStudentApplications(Model model, Principal principal) {
        List<Application> myApplications = applicationRepo.findByStudentEmail((userService.searchByEmail(principal.getName()).get()).getEmail());
        model.addAttribute("myApplications", myApplications);
        return "Student-Applications";
    }

}
