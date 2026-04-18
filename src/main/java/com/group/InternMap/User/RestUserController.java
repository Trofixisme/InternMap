package com.group.InternMap.User;

import com.group.InternMap.Application.Application;
import com.group.InternMap.Application.ApplicationService;
import com.group.InternMap.Job.JobPosting;
import com.group.InternMap.Job.JobPostingService;
import com.group.InternMap.Roadmap.Roadmap;
import com.group.InternMap.Roadmap.RoadmapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class RestUserController {

    UserService userService;
    RoadmapService roadmapService;
    JobPostingService jobPostingService;
    ApplicationService applicationService;

    @Autowired
    public RestUserController(RoadmapService roadmapService, UserService userService, JobPostingService jobPostingService, ApplicationService applicationService) {
        this.userService = userService;
        this.roadmapService = roadmapService;
        this.jobPostingService = jobPostingService;
        this.applicationService=applicationService;
    }

    // Display a specific roadmap with modules and skills
    @GetMapping("/{id:[0-9]+}")
    public Roadmap viewRoadmap(@PathVariable long id, Model model, Authentication authentication) {

        Roadmap roadmap = roadmapService.findRoadmapById(id);
        model.addAttribute("roadmap", roadmap);
        model.addAttribute("totalSkills", roadmapService.countTotalModules(roadmap));
        model.addAttribute("userRole", authentication != null ? authentication.getAuthorities().toString() : "");

        return roadmap;
    }

    //MARK: JobPostings


}