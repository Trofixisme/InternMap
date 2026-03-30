package com.group.InternMap.User;

import com.group.InternMap.Application.Application;
import com.group.InternMap.Job.JobPosting;
import com.group.InternMap.Job.JobPostingService;
import com.group.InternMap.Roadmap.Roadmap;
import com.group.InternMap.Roadmap.RoadmapService;
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
    RoadmapService roadmapService;
    JobPostingService jobPostingService;

    @Autowired
    public UserController(RoadmapService roadmapService, UserService userService, JobPostingService jobPostingService) {
        this.userService = userService;
        this.roadmapService = roadmapService;
        this.jobPostingService = jobPostingService;
    }

    // Display a specific roadmap with modules and skills
    @GetMapping("/{id}")
    public String viewRoadmap(@PathVariable long id, Model model, Authentication authentication) {

        Roadmap roadmap = roadmapService.findRoadmapById(id);
        model.addAttribute("roadmap", roadmap);
        model.addAttribute("totalSkills", roadmapService.countTotalModules(roadmap));
        model.addAttribute("userRole", authentication != null ? authentication.getAuthorities().toString() : "");

        return "roadmap/view";
    }

    //TODO: FIX
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

        model.addAttribute("isLoggedIn", authentication == null);

        if (authentication != null) {
            switch (authentication.getAuthorities().toString()) {
                case "[ROLE_ADMIN]" -> model.addAttribute("isAdmin", true);
                case "[ROLE_RECRUITER]" -> model.addAttribute("isRecruiter", true);
            }
        }

        ArrayList<JobPosting> jobPosting = (ArrayList<JobPosting>) jobPostingService.getAllJobPostings();
        model.addAttribute("jobPostings", jobPosting);

        return "JobPosting"; // Thymeleaf template
    }

    @PostMapping("/JobPostings/search")
    public String searchJobPosting(@RequestParam("searchQuery") String searchQuery, Model model) {
        List<JobPosting> results = jobPostingService.searchJobs(searchQuery);

        model.addAttribute("jobPostings", results);
        return "JobPosting";
    }
}