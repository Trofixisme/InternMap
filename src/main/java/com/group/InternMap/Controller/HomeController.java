package com.group.InternMap.Controller;

import com.group.InternMap.Recruiter.RecruiterRepo;
import com.group.InternMap.Admin.Admin;
import com.group.InternMap.Roadmap.RoadmapRepo;
import com.group.InternMap.User.UserRepo;
import com.group.InternMap.User.Users;
import com.group.InternMap.Recruiter.Recruiter;
import com.group.InternMap.Student.Student;
import jakarta.servlet.http.HttpSession;
import org.jboss.logging.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class HomeController {

    private final UserRepo userRepo;
    RoadmapRepo roadmapRepo;
    RecruiterRepo recruiterRepo;
    Logger logger = Logger.getLogger(HomeController.class.getName());

    public HomeController(RoadmapRepo roadmapRepo, RecruiterRepo recruiterRepo, UserRepo userRepo) {
        this.roadmapRepo = roadmapRepo;
        this.recruiterRepo = recruiterRepo;
        this.userRepo = userRepo;
    }

    @GetMapping("/")
    public String home(Model model, Principal principal, Authentication authentication) {
        if (principal != null) {
            logger.info("Accessed '/' with credientials: " + principal.getName());
            model.addAttribute("email", principal.getName());
            model.addAttribute("isLoggedIn", true);
        } else {
            logger.info("Accessed '/' without credientials");
            model.addAttribute("isLoggedIn", false);
        }

        if (authentication != null && authentication.getAuthorities().toString().equals("[ROLE_ADMIN]")) {
            model.addAttribute("role", "admin");
        }

        model.addAttribute("roadmaps", roadmapRepo.findAll());

        return "index";
    }

    @GetMapping("/signup-choice")
    public String signupChoice() { // or whatever your object is
        return "InternMapSignUpChoice"; // Note: no .htm; extension
    }

    @GetMapping("/profile")
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public String showProfile(Model model, Principal principal) {
        Users user;
        if (principal != null) {
            user = userRepo.findByEmail(principal.getName()).get();
        } else {
            return "redirect:/";
        }
        //Stupid way to resolve the "Edit CV" button not showing up whenever it should show
        //or showing up when it shouldn't.
        model.addAttribute("loggedInUser", user);
        model.addAttribute("user", user);

        switch (user) {
            case Student _ -> {
                model.addAttribute("student", user);
                model.addAttribute("type", "student");
                return "profile";
            }

            case Recruiter recruiterUser -> {

                Recruiter recruiter = recruiterRepo
                        .findRecruiterWithCompanies(recruiterUser.getId())
                        .orElseThrow(() -> new RuntimeException("Recruiter not found"));

                model.addAttribute("recruiter", recruiter);
                model.addAttribute("type", "recruiter");

                return "profile";
            }

            case Admin _ -> {
                model.addAttribute("admin", user);
                model.addAttribute("type", "admin");
                return "profile";
            }

            default -> {}
        }

        return "index";
    }

    @GetMapping("/roadmaps")
    public String ViewRoadmaps(@ModelAttribute("user") Users users, Model model) {
        try {
        //          List<Roadmap> roadmaps = userService.viewRoadmaps();
        //          model.addAttribute("roadmaps", roadmaps);
          return "index";
        }
        catch(Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }

    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // This removes the session and all attributes, including the "loggedInUser"
        session.setAttribute("loggedInUser", null);

        // Redirect the user back to the login page
        return "redirect:/";
    }
}