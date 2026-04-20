package com.group.InternMap.Admin;

import com.group.InternMap.DTO.DashboardResponse;
import com.group.InternMap.DTO.RoadmapModuleSkill;
import com.group.InternMap.Roadmap.Roadmap;
import com.group.InternMap.Roadmap.RoadmapModuleRepo;
import com.group.InternMap.Roadmap.RoadmapRepo;
import com.group.InternMap.Roadmap.RoadmapService;
import com.group.InternMap.Skill.SkillRepo;
import com.group.InternMap.User.UserRepo;
import com.group.InternMap.User.UserRole;
import com.group.InternMap.User.UserService;
import com.group.InternMap.User.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
@RequestMapping("/REST")
public class RestAdminController {

    private final UserRepo userRepo;
    RoadmapRepo roadmapRepo;
    SkillRepo skillRepo;
    RoadmapModuleRepo roadmapModuleRepo;
    UserService userService;
    RoadmapService roadmapService;

    Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    public RestAdminController(RoadmapRepo roadmapRepo, SkillRepo skillRepo, RoadmapModuleRepo roadmapModuleRepo, UserService userService, UserRepo userRepo , RoadmapService roadmapService) {
        this.roadmapRepo = roadmapRepo;
        this.skillRepo = skillRepo;
        this.roadmapModuleRepo = roadmapModuleRepo;
        this.userService = userService;
        this.userRepo = userRepo;
        this.roadmapService = roadmapService;
    }

    @PostMapping("/admin/register")
    public void registerAdmin(HttpServletRequest request, @RequestBody Admin user) throws ServletException {
        user.setRole(UserRole.ADMIN);
        userService.register(user, request);
    }

    @PostMapping("/new/roadmap")
    public void createRoadmap(@RequestBody RoadmapModuleSkill dto, Authentication authentication) {

        if (authentication != null && authentication.getAuthorities().toString().equals("[ROLE_" + UserRole.ADMIN + "]")) {
            Roadmap roadmap = dto.toRoadmap();
            roadmapRepo.save(roadmap);
        } else {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "User must be of "+ UserRole.ADMIN +" to proceed");
        }
    }

    //Update roadmap
    //This method most probably doesn't work
    @PostMapping("/{id}")
    public void updateRoadmap(@PathVariable long id, @RequestBody Roadmap roadmap, Authentication authentication) {

        if (authentication != null && authentication.getAuthorities().toString().equals("[ROLE_" + UserRole.ADMIN + "]")) {
            roadmap.setId(id);
            roadmapRepo.save(roadmap);
        } else {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "User must be of "+ UserRole.ADMIN +" to proceed");
        }
    }

    @PostMapping("/roadmaps/{id}/delete")
    public void deleteRoadmap(@PathVariable Long id, Authentication authentication) {

        if (authentication != null && authentication.getAuthorities().toString().equals("[ROLE_" + UserRole.ADMIN + "]")) {
            roadmapRepo.deleteById(id);

        } else {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "User must be of "+ UserRole.ADMIN +" to proceed");
        }
    }

    @GetMapping("/dashboard")
    public DashboardResponse getAdminDashboard(Authentication authentication ) {
        if (authentication == null || !authentication.getAuthorities().toString().equals("[ROLE_" + UserRole.ADMIN + "]")) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "Access denied");
        }
        List<Users> users = userService.findall();
        List<Roadmap> roadmaps = roadmapService.findAll();

        return new DashboardResponse(users, roadmaps);
    }
    @PostMapping("/dashboard/delete")
    public void deleteUser(@RequestParam String email) {
            userService.deleteByEmail(email);
    }


}


