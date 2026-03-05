package com.group.InternMap.Admin;

import com.group.InternMap.DTO.RoadmapModuleSkill;
import com.group.InternMap.Roadmap.Roadmap;
import com.group.InternMap.Roadmap.RoadmapModule;
import com.group.InternMap.Skill.Skill;
import com.group.InternMap.User.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.util.UUID;

import static com.group.InternMap.Deprecated.Repository.RepositoryAccessors.*;

@RestController("/api/admin")
public class adminController {
    UserService userService;
    @GetMapping("/admin/register")
    public String showRegisterAdmin(Model model) {
        model.addAttribute("user", new Admin());
        return "adminRegister";
    }

    @PostMapping("/admin/register")
    public String registerAdmin(@ModelAttribute("user") Admin user, Model model) {
        try {
            if (UserService.isEmailValid(user.getEmail())) {
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
    @GetMapping("/new")
    public String newRoadmap(Model model, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null || !(session.getAttribute("loggedInUser") instanceof Admin admin)) {
            return "redirect:/login";
        }

        System.out.println(admin);
        RoadmapModuleSkill dto = new RoadmapModuleSkill();
        RoadmapModuleSkill.ModuleData module = new RoadmapModuleSkill.ModuleData();
        RoadmapModuleSkill.SkillData skill = new RoadmapModuleSkill.SkillData();
        module.getSkills().add(skill);
        dto.getModules().add(module);
        model.addAttribute("roadmaps", dto);
        return "roadmap/form";
    }

    @PostMapping("/new")
    public String createRoadmap(@ModelAttribute("roadmap") RoadmapModuleSkill dto, HttpSession session) throws MalformedURLException {
        if (session.getAttribute("loggedInUser") == null || !(session.getAttribute("loggedInUser") instanceof Admin admin)) {
            return "redirect:/login";
        }
        Roadmap roadmap = dto.toRoadmap();

        allRoadmaps.add(roadmap);

        for (RoadmapModule module : roadmap.getAllModules()) {
            allmodules.add(module);
            for (Skill skill : module.getAllSkills()) {
                allskills.add(skill);
            }
        }

        return "redirect:/roadmaps/" + roadmap.getId();
    }
    //Display form to edit roadmap
    @GetMapping("/{id}/edit")
    public String editRoadmap(@PathVariable long id, Model model) {
        try {
            Roadmap roadmap = roadmapService.findRoadmapById(id);
            model.addAttribute("roadmap", roadmap);
            return "roadmap/form";
        } catch (Exception e) {
            model.addAttribute(e);
            return "redirect:/roadmaps";
        }
    }

    //Update roadmap
    @PostMapping("/{id}")
    public String updateRoadmap(@PathVariable UUID id, @ModelAttribute Roadmap roadmap) {
        allRoadmaps.add(roadmap);
        return "redirect:/roadmaps/" + id;
    }

    //Delete roadmap
    @PostMapping("/{id}/delete")
    public String deleteRoadmap(@PathVariable long id) {
        roadmapService.deleteById(id);
        return "redirect:/roadmaps";
    }
}
