package com.group.InternMap.Student;

import com.group.InternMap.Application.Application;
import com.group.InternMap.Application.CV;
import com.group.InternMap.DTO.ApplicationAndCVDTO;
import com.group.InternMap.Job.JobPosting;
import com.group.InternMap.Job.JobPostingService;
import com.group.InternMap.User.UserService;
import com.group.InternMap.User.Users;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.group.InternMap.Deprecated.Repository.RepositoryAccessors.ALL_USERS;
import static com.group.InternMap.Deprecated.Repository.RepositoryAccessors.allApplications;

@Controller
public class StudentController {

    JobPostingService jobPostingService;
    UserService userService;

    @GetMapping("/student/register")
    public String showRegisterStudent(Model model) {
        model.addAttribute("user", new Student());
        return "StudentRegister";
    }

    @PostMapping("/student/register")
    public String registerStudent(@ModelAttribute("user") Student user, Model model) {
        try {
            var email = user.getEmail();
            if (UserService.isEmailValid(email)) {
                userService.register(user);
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            // Return the view name (DO NOT REDIRECT)
            return "StudentRegister";
        }
        // Only redirect on SUCCESS
        return "redirect:/login";
    }
    @GetMapping("/cv")
    public String cv(Model model, HttpSession session) {
        // Fixed: Use correct attribute name
        Users users = (Users) session.getAttribute("loggedInUser");
        if (users == null) {
            return "redirect:/login";
        }
        if (!(users instanceof Student student)) {
            return "redirect:/profile";
        }

        if (student.getCv() != null) {
            model.addAttribute("cv", student.getCv());
        } else {
            model.addAttribute("cv", new CV());
        }

        return "CV";
    }

    @PostMapping("/cv/save")
    public String saveCV(@ModelAttribute("cv") CV cv, HttpSession session) {
        Users loggedUsers = (Users) session.getAttribute("loggedInUser");

        if (loggedUsers == null) {
            return "redirect:/login";
        }

        if (!(loggedUsers instanceof Student student)) {
            return "redirect:/profile";
        }

        if(ALL_USERS.contains(student)) {
//            allUsers.remove(student);
            if (student.getCv() != null) {
                System.out.println(student.getCv());
                CV existingCV = student.getCv();
                existingCV.setDescription(cv.getDescription());
                existingCV.setPastExperiences(cv.getPastExperiences());
                existingCV.setProjects(cv.getProjects());
                student.setCv(existingCV);
                System.out.println(existingCV);
//                allUsers.add(student);
            } else {
                student.setCv(cv);
//                allUsers.add(student);
            }
        }
        // Update session
        session.setAttribute("loggedInUser", student);
        return "redirect:/profile";
    }

    @GetMapping("/applications/new")
    public String createNewApplication(@RequestParam("jobId") long jobPostingId, ApplicationAndCVDTO applicationandCVDTO, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }
        JobPosting jobPosting=jobPostingService.findJobpostingByID(jobPostingId);
        if(jobPosting==null){
            redirectAttributes.addFlashAttribute("error","Job posting not found");
            return "redirect:/jobPosting";
        }

        model.addAttribute("jobId", jobPostingId);
        model.addAttribute("applicationandCVDTO", new ApplicationAndCVDTO());
        model.addAttribute("jobPosting", jobPosting); // Add job details for displaying
        return "Application";
    }

    @PostMapping("/application/save")
    public String saveApplication(@RequestParam("jobId") long jobId,
                                  @ModelAttribute ApplicationAndCVDTO applicationandCVDTO,
                                  Model model, HttpSession session,
                                  RedirectAttributes redirectAttributes)  {

        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }
//        Application application = applicationandCVDTO.getApplication();
        Student user = (Student) session.getAttribute("loggedInUser");
        if(user.getCv() == null){
            redirectAttributes.addFlashAttribute("error","CV not found");
            return "redirect:/cv";
        }
//        applicationandCVDTO.setStudent(user);
//        UUID jobPostingID = jobId;
        try {
            JobPosting jobPosting=jobPostingService.findJobpostingByID(jobId);

            System.out.println(jobPosting);
            if (jobPosting == null) {
                redirectAttributes.addFlashAttribute("error","Job posting not found");
                return "redirect:/JobPostings";

            }

            Application application = applicationandCVDTO.getApplication();
//            application.setCv(user.getCv());
            applicationandCVDTO.setStudent(user);
            jobPosting.addApplication(application);
            allApplications.add(application);
            redirectAttributes.addFlashAttribute("message", "Application saved successfully");
            return "redirect:/JobPostings";
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            model.addAttribute("error", "Error saving application: " + e.getMessage());
            return "redirect:/applications/new?jobId=" + jobId;
        }

    }
}
