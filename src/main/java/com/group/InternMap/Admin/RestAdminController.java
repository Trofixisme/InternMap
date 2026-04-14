package com.group.InternMap.Admin;

import com.group.InternMap.DTO.RoadmapModuleSkill;
import com.group.InternMap.Roadmap.Roadmap;
import com.group.InternMap.Roadmap.RoadmapModuleRepo;
import com.group.InternMap.Roadmap.RoadmapRepo;
import com.group.InternMap.Skill.SkillRepo;
import com.group.InternMap.User.UserRole;
import com.group.InternMap.User.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/REST")
public class RestAdminController {

    RoadmapRepo roadmapRepo;
    SkillRepo skillRepo;
    RoadmapModuleRepo roadmapModuleRepo;
    UserService userService;

    Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    public RestAdminController(RoadmapRepo roadmapRepo, SkillRepo skillRepo, RoadmapModuleRepo roadmapModuleRepo, UserService userService) {
        this.roadmapRepo = roadmapRepo;
        this.skillRepo = skillRepo;
        this.roadmapModuleRepo = roadmapModuleRepo;
        this.userService = userService;
    }

    @PostMapping("/admin/register")
    public void registerAdmin(HttpServletRequest request, @ModelAttribute("user") Admin user) throws ServletException {
        user.setRole(UserRole.ADMIN);
        userService.register(user, request);
    }

    @PostMapping("/new/roadmap")
    public void createRoadmap(@ModelAttribute("roadmaps") RoadmapModuleSkill dto, Authentication authentication) {

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
    public void updateRoadmap(@PathVariable long id, @ModelAttribute Roadmap roadmap, Authentication authentication) {

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
}
