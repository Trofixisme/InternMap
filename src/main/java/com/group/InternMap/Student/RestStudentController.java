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



}
