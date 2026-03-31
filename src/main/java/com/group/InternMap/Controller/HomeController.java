package com.group.InternMap.Controller;

import com.group.InternMap.Admin.Admin;
import com.group.InternMap.Recruiter.RecruiterService;
import com.group.InternMap.Roadmap.RoadmapRepo;
import com.group.InternMap.Roadmap.RoadmapService;
import com.group.InternMap.User.UserService;
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

    private final UserService userService;
    RoadmapService roadmapService;
    RecruiterService recruiterService;

    Logger logger = Logger.getLogger(HomeController.class.getName());

    public HomeController(RoadmapService roadmapService, RecruiterService recruiterService, UserService userService) {
        this.roadmapService = roadmapService;
        this.recruiterService = recruiterService;
        this.userService = userService;
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

        model.addAttribute("roadmaps", roadmapService.findAll());

        return "index";
    }

    @GetMapping("/signup-choice")
    public String signupChoice() {
        // Note: no .htm; extension
        return "InternMapSignUpChoice";
    }

    @GetMapping("/profile")
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public String showProfile(Model model, Principal principal) {
        Users user;
        if (principal != null) {
            user = userService.searchByEmail(principal.getName()).get();
        } else {
            return "redirect:/";
        }

        //Stupid way to resolve the "Edit CV" button not showing up whenever it should show
        //or showing up when it shouldn't.
//        model.addAttribute("loggedInUser", user); // fixed that thinge ( hopefully nothing breaks )
        model.addAttribute("user", user);

        switch (user) {
            case Student _ -> {
                model.addAttribute("student", user);
                model.addAttribute("type", "student");
                return "profile";
            }

            case Recruiter _ -> {

                Recruiter recruiter = recruiterService
                        .findRecruiterById(user.getId());

                model.addAttribute("recruiter", recruiter);
                model.addAttribute("type", "recruiter");

                return "profile";
            }

            case Admin _ -> {
                model.addAttribute("admin", user);
                model.addAttribute("type", "admin");
                return "profile";
            }

            default -> {
                return "index";
            }
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