package com.group.InternMap.Controller;

import com.group.InternMap.Recruiter.RecruiterService;
import com.group.InternMap.Roadmap.Roadmap;
import com.group.InternMap.Roadmap.RoadmapService;
import com.group.InternMap.User.UserService;
import com.group.InternMap.User.Users;
import jakarta.servlet.http.HttpSession;
import org.jboss.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/REST")
public class RestHomeController {

    private final UserService userService;
    RoadmapService roadmapService;
    RecruiterService recruiterService;

    Logger logger = Logger.getLogger(HomeController.class.getName());

    public RestHomeController(RoadmapService roadmapService, RecruiterService recruiterService, UserService userService) {
        this.roadmapService = roadmapService;
        this.recruiterService = recruiterService;
        this.userService = userService;
    }

    @GetMapping("/")
    public List<Roadmap> home() {

        return roadmapService.findAll();
    }

    @GetMapping("/profile")
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public Users showProfile(Principal principal) {
        Users user;
        if (principal != null) {
            user = userService.searchByEmail(principal.getName()).get();
        } else {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Must be signed into an account");
        }

        return user;
    }

    //For some reason this just doesn't work
    @PostMapping("/logout")
    public void logout(HttpSession session) {

        session.invalidate();
    }
}
