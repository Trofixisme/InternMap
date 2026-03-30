package com.group.InternMap.User;

import com.group.InternMap.Application.Application;
import com.group.InternMap.Application.ApplicationService;
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
    ApplicationService applicationService;

    @Autowired
    public UserController(RoadmapService roadmapService, UserService userService, JobPostingService jobPostingService, ApplicationService applicationService) {
        this.userService = userService;
        this.roadmapService = roadmapService;
        this.jobPostingService = jobPostingService;
        this.applicationService=applicationService;
    }

    // Display a specific roadmap with modules and skills
    @GetMapping("/{id:[0-9]+}")
    public String viewRoadmap(@PathVariable long id, Model model, Authentication authentication) {

        Roadmap roadmap = roadmapService.findRoadmapById(id);
        model.addAttribute("roadmap", roadmap);
        model.addAttribute("totalSkills", roadmapService.countTotalModules(roadmap));
        model.addAttribute("userRole", authentication != null ? authentication.getAuthorities().toString() : "");

        return "roadmap/view";
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

    @PostMapping("/JobPostings/{jobId}/applications/search")
    public String searchApplication(@PathVariable Long jobId, @RequestParam("searchQuery") String searchQuery, Model model) {
            List<Application> results = applicationService.searchApplication(searchQuery);
        JobPosting job = jobPostingService.findJobPostingByID(jobId);
        model.addAttribute("applications", results);
        model.addAttribute("jobPosting", job);
        model.addAttribute("query", searchQuery);
        return "ViewApplicationDetail";
    }
}