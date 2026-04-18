package com.group.InternMap.Roadmap;

import com.group.InternMap.DTO.RoadmapModuleSkill;
import com.group.InternMap.User.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/api/roadmap")
public class RoadmapController {
    RoadmapRepo roadmapRepo;
    @Autowired
    RoadmapController(RoadmapRepo roadmapRepo){
        this.roadmapRepo=roadmapRepo;

    }

    @PostMapping("/new")
    public void createRoadmap(@RequestBody RoadmapModuleSkill dto, Authentication authentication) {

        if (authentication != null && authentication.getAuthorities().toString().equals("[ROLE_" + UserRole.ADMIN + "]")) {
            Roadmap roadmap = dto.toRoadmap();
            roadmapRepo.save(roadmap);
        } else {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "User must be of "+ UserRole.ADMIN +" to proceed");
        }
    }
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
}
