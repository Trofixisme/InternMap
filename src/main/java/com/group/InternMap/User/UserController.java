package com.group.InternMap.User;

import com.group.InternMap.Application.Application;
import com.group.InternMap.Recruiter.RecruiterService;
import com.group.InternMap.Roadmap.Roadmap;
import com.group.InternMap.Roadmap.RoadmapRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final RecruiterService recruiterService;
    private final RoadmapRepo roadmapRepo;

    public UserController(RoadmapRepo roadmapRepo,UserService userService, RecruiterService recruiterService) {
        this.userService = userService;
        this.recruiterService = recruiterService;
        this.roadmapRepo=roadmapRepo;
    }
    @GetMapping("/roadmaps")
    public String ViewRoadmaps() {
        try {
            return roadmapRepo.findAll().toString();
        }
        catch(Exception e){
            return "Failed to load roadmaps: " + e.getMessage();
        }
    }

    //Display a specific roadmap with modules and skills
    @GetMapping("/{id}")
    public String viewRoadmap(@PathVariable long id, Model model) {
        try {
            Roadmap roadmap = roadmapRepo.findRoadmapById(id);
            int totalSkills = roadmap.getAllModules().stream()
                    .mapToInt(module -> module.getAllSkills() != null ? module.getAllSkills().size() : 0)
                    .sum();
            model.addAttribute("roadmap", roadmap);
            model.addAttribute("totalSkills", totalSkills);
            return "roadmap/view";
        }
        catch(Exception e){
            model.addAttribute(e);
            return "redirect:/roadmaps";
        }
    }

    @PostMapping("/application/search")
    public String searchJobPosting(@RequestParam("searchQuery") String searchQuery, @ModelAttribute Application application, Model model, HttpSession session) {
        try {
            // Search dynamically using your service
//            List<Application> results = recruiterService.searchApplication(searchQuery.replaceFirst(",", ""));
            // Add search results to the model
//            model.addAttribute("applications", results);
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

    @PostMapping("/login")
    public String login(@ModelAttribute("user") Users users, Model model, HttpSession session) {
        try {
            Users authenticatedUsers = userService.login(users.getEmail(), users.getPlainPassword());
            session.setAttribute("loggedInUser", authenticatedUsers);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "login";
        }
        System.out.println("User's logged in");
        return "redirect:/";
    }
}