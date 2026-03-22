package com.group.InternMap.Admin;

import com.group.InternMap.DTO.RoadmapModuleSkill;
import com.group.InternMap.Roadmap.Roadmap;
import com.group.InternMap.Roadmap.RoadmapModuleRepo;
import com.group.InternMap.Roadmap.RoadmapRepo;
import com.group.InternMap.Skill.SkillRepo;
import com.group.InternMap.User.UserRole;
import com.group.InternMap.User.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller("/admin")
@EnableMethodSecurity
public class AdminController {

    RoadmapRepo roadmapRepo;
    SkillRepo skillRepo;
    RoadmapModuleRepo roadmapModuleRepo;
    UserService userService;

    Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    public AdminController(RoadmapRepo roadmapRepo, SkillRepo skillRepo, RoadmapModuleRepo roadmapModuleRepo, UserService userService) {
        this.roadmapRepo = roadmapRepo;
        this.skillRepo = skillRepo;
        this.roadmapModuleRepo = roadmapModuleRepo;
        this.userService = userService;
    }

    @GetMapping("/admin/register")
    public String showRegisterAdmin(Model model) {
        model.addAttribute("user", new Admin());
        return "adminRegister";
    }

    @PostMapping("/admin/register")
    public String registerAdmin(@ModelAttribute("user") Admin user, Model model) {
        try {
            if (UserService.isEmailValid(user.getEmail())) {
                user.setRole(UserRole.ADMIN);
                userService.register(user);
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            // Return the view name (DO NOT REDIRECT)
            return "adminRegister";
        }

        // Only redirect on SUCCESS
        return "redirect:/login";
    }

    //Display form to create a new roadmap
    @GetMapping("/new/roadmap")
    @PreAuthorize("hasRole('ADMIN')")
    public String newRoadmap(Model model) {

        RoadmapModuleSkill dto = new RoadmapModuleSkill();
        RoadmapModuleSkill.ModuleData module = new RoadmapModuleSkill.ModuleData();
        RoadmapModuleSkill.SkillData skill = new RoadmapModuleSkill.SkillData();
        module.getSkills().add(skill);
        dto.getModules().add(module);
        model.addAttribute("roadmaps", dto);

        return "roadmap/form";
    }

    @PostMapping("/new/roadmap")
    @PreAuthorize("hasRole('ADMIN')")
    public String createRoadmap(@ModelAttribute("roadmaps") RoadmapModuleSkill dto) {

        try {
            Roadmap roadmap = dto.toRoadmap(); // Convert DTO → Entity
            roadmapRepo.save(roadmap);         // Cascade saves modules & skills
//            return "redirect:/roadmaps/" + roadmap.getId();
            return "redirect:/";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "redirect:/admin/roadmaps/new?error=true";
        }
    }

    //Display form to edit roadmap
    @GetMapping("/{id}/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String editRoadmap(@PathVariable long id, Model model) {
        try {
            Roadmap roadmap = roadmapRepo.findRoadmapById(id);
            model.addAttribute("roadmap", roadmap);
            model.addAttribute("modules", roadmap.getAllModules());
            return "roadmap/form";
        } catch (Exception e) {
            model.addAttribute(e);
            return "redirect:/roadmaps";
        }
    }

    //Update roadmap
    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateRoadmap(@PathVariable long id, @ModelAttribute Roadmap roadmap) {
        roadmapRepo.save(roadmap);
        return "redirect:/roadmaps/" + id;
    }

    @PostMapping("/roadmaps/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteRoadmap(@PathVariable Long id) {

        try {
            roadmapRepo.deleteById(id);
            return "redirect:/";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
