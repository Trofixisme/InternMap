package com.group.InternMap.User;

import com.group.InternMap.Application.Application;
import com.group.InternMap.Job.JobPosting;
import com.group.InternMap.Job.JobPostingService;
import com.group.InternMap.Job.JobRepo;
import com.group.InternMap.Roadmap.Roadmap;
import com.group.InternMap.Roadmap.RoadmapRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    UserService userService;
    RoadmapRepo roadmapRepo;
    JobPostingService jobPostingService;
    JobRepo jobRepo;

    @Autowired
    public UserController(RoadmapRepo roadmapRepo, UserService userService, JobPostingService jobPostingService, JobRepo jobRepo) {
        this.userService = userService;
        this.roadmapRepo = roadmapRepo;
        this.jobPostingService = jobPostingService;
        this.jobRepo = jobRepo;
    }

    // Display a specific roadmap with modules and skills
    @GetMapping("/{id}")
    public String viewRoadmap(@PathVariable long id, Model model, Authentication authentication) {

        try {
            Roadmap roadmap = roadmapRepo.findRoadmapById(id);
            int totalSkills = roadmap.getAllModules().stream()
                    .mapToInt(module -> module.getAllSkills() != null ? module.getAllSkills().size() : 0)
                    .sum();
            model.addAttribute("roadmap", roadmap);
            model.addAttribute("totalSkills", totalSkills);
            model.addAttribute("userRole", authentication != null ? authentication.getAuthorities().toString() : "");
            return "roadmap/view";
        } catch(Exception e) {
            model.addAttribute(e);
            return "redirect:/roadmaps";
        }
    }

    @PostMapping("/application/search")
    public String searchJobPosting(@RequestParam("searchQuery") String searchQuery, @ModelAttribute Application application, Model model) {
        try {
            // Search dynamically using your service
//             List<Application> results = recruiterService.searchApplication(searchQuery.replaceFirst(",", ""));
            // Add search results to the model
//             model.addAttribute("applications", results);
            // Add the job posting object to the model so form fields keep their values
            model.addAttribute("application", application);
            model.addAttribute("jobPosting", null);
        } catch (Exception e) {
            model.addAttribute("error", "Error searching application: " + e.getMessage());
        }

        return "ViewApplicationDetail";
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("user", new Users());
        return "login";
    }

    //MARK: JobPostings
    @GetMapping("/JobPostings")
    public String getAllJobPostings(Model model, Authentication authentication) {
        try {
            model.addAttribute("isLoggedIn", authentication == null);

            if (authentication != null) {
                switch (authentication.getAuthorities().toString()) {
                    case "[ROLE_ADMIN]" -> model.addAttribute("isAdmin", true);
                    case "[ROLE_RECRUITER]" -> model.addAttribute("isRecruiter", true);
                }
            }

            ArrayList<JobPosting> jobPosting = (ArrayList<JobPosting>) jobPostingService.getAllJobPostings();
            model.addAttribute("jobPostings", jobPosting);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            model.addAttribute("error", "Failed to load job postings");
        }
        return "JobPosting"; // Thymeleaf template
    }

    @PostMapping("/JobPostings/search")
    public String searchJobPosting(@RequestParam("searchQuery") String searchQuery, Model model) {
        try {
            // Search dynamically using your service
            List<JobPosting> results = jobRepo.searchJobs(searchQuery);
            // Add search results to the model
            model.addAttribute("jobPostings", results);
        } catch (Exception e) {
            model.addAttribute("error", "Error searching application: " + e.getMessage());
        }
        return "JobPosting";
    }

}