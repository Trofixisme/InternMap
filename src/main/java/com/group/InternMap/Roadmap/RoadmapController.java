package com.group.InternMap.Roadmap;

import com.group.InternMap.DTO.RoadmapModuleSkill;
import com.group.InternMap.Admin.Admin;
import com.group.InternMap.Skill.Skill;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.util.UUID;


import static com.group.InternMap.Deprecated.Repository.RepositoryAccessors.*;

@Controller
@RequestMapping("/roadmaps")
public class RoadmapController {

    private final RoadmapService roadmapService = new RoadmapService();

    //Display a specific roadmap with modules and skills
    @GetMapping("/{id}")
    public String viewRoadmap(@PathVariable UUID id, Model model) {
        try {
            Roadmap roadmap = roadmapService.findRoadmapbyId(id);
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
    public String editRoadmap(@PathVariable UUID id, Model model) {
        try {
            Roadmap roadmap = roadmapService.findRoadmapbyId(id);
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
    public String deleteRoadmap(@PathVariable UUID id) {
        roadmapService.deleteById(id);
        return "redirect:/roadmaps";
    }
}