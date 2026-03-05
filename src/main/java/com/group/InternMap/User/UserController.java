package com.group.InternMap.User;

import com.group.InternMap.Admin.Admin;
import com.group.InternMap.Application.Application;
import com.group.InternMap.DTO.RecruiterRegistrationDTO;
import com.group.InternMap.Company.Company;
import com.group.InternMap.Recruiter.Recruiter;
import com.group.InternMap.Deprecated.Repository.RepositoryAccessors;
import com.group.InternMap.Company.CompanyService;
import com.group.InternMap.Recruiter.RecruiterService;
import com.group.InternMap.Roadmap.Roadmap;
import com.group.InternMap.Roadmap.RoadmapRepo;
import com.group.InternMap.Student.Student;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.group.InternMap.Deprecated.Repository.RepositoryAccessors.ALL_USERS;
import static com.group.InternMap.Deprecated.Repository.RepositoryAccessors.allCompanies;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final RecruiterService recruiterService;
    private final CompanyService companyService;
    private final RoadmapRepo roadmapRepo;

    public UserController(RoadmapRepo roadmapRepo,UserService userService, RecruiterService recruiterService, CompanyService companyService) {
        this.userService = userService;
        this.recruiterService = recruiterService;
        this.companyService = companyService;
        this.roadmapRepo=roadmapRepo;
    }
    @GetMapping("/roadmaps")
    public String ViewRoadmaps() throws Exception{
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
            Roadmap roadmap = roadmapService.findRoadmapById(id);
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
            List<Application> results = recruiterService.searchApplication(searchQuery.replaceFirst(",", ""));
            // Add search results to the model
            model.addAttribute("applications", results);
            // Add the jobposting object to the model so form fields keep their values
            model.addAttribute("application", application);
            model.addAttribute("jobPosting", null);
        } catch (Exception e) {
            model.addAttribute("error", "Error searching application: " + e.getMessage());
        }
        return "ViewApplicationDetail";
    }


    @GetMapping("/login")
    public String showloginPage(Model model) {
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