package com.group.InternMap.Controller;

import com.group.InternMap.Recruiter.RecruiterRepo;
import com.group.InternMap.Admin.Admin;
import com.group.InternMap.Roadmap.RoadmapRepo;
import com.group.InternMap.User.Users;
import com.group.InternMap.Recruiter.Recruiter;
import com.group.InternMap.Student.Student;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class HomeController {

    RoadmapRepo roadmapRepo;
    RecruiterRepo recruiterRepo;

    public HomeController(RoadmapRepo roadmapRepo,RecruiterRepo recruiterRepo) {
        this.roadmapRepo = roadmapRepo;
        this.recruiterRepo = recruiterRepo;
    }

//    @GetMapping("/")
//    public String showHomePage(Model model, HttpSession session) {
//
//        if (session.getAttribute("loggedInUser") instanceof Admin) {
//            model.addAttribute("isLoggedIn", true);
//            model.addAttribute("isAdmin", true);
//        } else if (session.getAttribute("loggedInUser") == null) {
//            model.addAttribute("isLoggedIn", false);
//            model.addAttribute("isAdmin", false);
//        } else {
//            model.addAttribute("isLoggedIn", true);
//            model.addAttribute("isAdmin", false);
//        }
//
//        model.addAttribute("roadmaps", roadmapRepo.findAll());
//
//        return "index";
//    }
//@GetMapping("/")
//public String showHomePage(Model model, Principal principal) {
//
//    if (principal != null) {
//        model.addAttribute("isLoggedIn", true);
//        model.addAttribute("email", principal.getName());
//    } else {
//        model.addAttribute("isLoggedIn", false);
//    }
//
//    model.addAttribute("roadmaps", roadmapRepo.findAll());
//
//    return "index";
//}
@GetMapping("/")
public String home(Model model, Principal principal) {

    System.out.println("Principal: " + principal);

    if (principal != null) {
        System.out.println("User email: " + principal.getName());
        model.addAttribute("email", principal.getName());
        model.addAttribute("isLoggedIn", true);
    } else {
        System.out.println("User is NOT logged in");
        model.addAttribute("isLoggedIn", false);
    }

    return "index";
}

    @GetMapping("/signup-choice")
    public String signupChoice() { // or whatever your object is
        return "InternMapSignUpChoice"; // Note: no .htm; extension
    }

    @GetMapping("/profile")
    public String showProfile(Model model, HttpSession session) {
        Users loggedUsers = (Users) session.getAttribute("loggedInUser");
        if (loggedUsers == null) {
            return "redirect:/login";
        }

        //Stupid way to resolve the "Edit CV" button not showing up whenever it should show
        //or showing up when it shouldn't.
        model.addAttribute("loggedInUser", loggedUsers);
        model.addAttribute("user", loggedUsers);

        switch (loggedUsers) {
            case Student _ -> {
                model.addAttribute("student", loggedUsers);
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
                model.addAttribute("admin", loggedUsers);
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
        catch(Exception e){
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