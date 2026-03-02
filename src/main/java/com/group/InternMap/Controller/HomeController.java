package com.group.InternMap.Controller;

import com.group.InternMap.Roadmap.Roadmap;
import com.group.InternMap.Admin.Admin;
import com.group.InternMap.User.Users;
import com.group.InternMap.Recruiter.Recruiter;
import com.group.InternMap.User.UserService;
import com.group.InternMap.Student.Student;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import static com.group.InternMap.Deprecated.Repository.RepositoryAccessors.allRoadmaps;

@Controller
public class HomeController {

    private final UserService userService;
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String showHomePage(Model model, HttpSession session) {

        if (session.getAttribute("loggedInUser") instanceof Admin) {
            model.addAttribute("isLoggedIn", true);
            model.addAttribute("isAdmin", true);
        } else if (session.getAttribute("loggedInUser") == null) {
            model.addAttribute("isLoggedIn", false);
            model.addAttribute("isAdmin", false);
        } else {
            model.addAttribute("isLoggedIn", true);
            model.addAttribute("isAdmin", false);
        }

        model.addAttribute("roadmaps", allRoadmaps);

        return "index";
    }
    @GetMapping("/signup-choice")
    public String signupChoice() { // or whatever your object is
        return "InternMapSignUpchoice"; // Note: no .htm; extension
    }

    @GetMapping("/user/home")
    public String showHomePage(Model model, ArrayList<Roadmap> roadmaps, HttpSession session) {
        System.out.println("Home page accessed, but without an active session ");
        // Retrieve the User object from the session
        Users users = (Users) session.getAttribute("loggedInUser");
        // 2. If no user is found in the session, redirect to the login page
        if (users == null) {
            return "redirect:/login";
        }
        //  Pass the user object to the Thymeleaf model for display
//        model.addAttribute("user", user);
        model.addAttribute("roadmaps", allRoadmaps);
        return "index";
    }

    @GetMapping("/profile")
    public String showStudentProfile(Model model, HttpSession session) {
        Users loggedUsers = (Users) session.getAttribute("loggedInUser");
        if (loggedUsers == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", loggedUsers);

        switch (loggedUsers) {
            case Student _ -> {
                model.addAttribute("student", loggedUsers);
                model.addAttribute("type", "student");
                return "profile";
            }
            case Recruiter _ -> {
                model.addAttribute("recruiter", loggedUsers);
                model.addAttribute("type", "recruiter");
                return "profile";
            }
            case Admin _ -> {
                model.addAttribute("admin", loggedUsers);
                model.addAttribute("type", "admin");
                return "profile";
            }
            default -> {
            }
        }

        return "index";
    }

    @GetMapping("/roadmaps")
    public String ViewRoadmaps(@ModelAttribute("user") Users users, Model model){
      try {
          List<Roadmap> roadmaps = userService.viewRoadmaps();
          model.addAttribute("roadmaps", roadmaps);
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
