package com.group.InternMap.Roadmap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoadmapService {

    RoadmapRepo roadmapRepo;

    @Autowired
    public RoadmapService(RoadmapRepo roadmapRepo) {
        // without this, the controller will have empty data
        this.roadmapRepo = roadmapRepo;
    }

    public int countTotalModules(Roadmap roadmap) {
        return roadmap.getAllModules().stream()
                .mapToInt(module -> module.getAllSkills() != null ? module.getAllSkills().size() : 0)
                .sum();
    }

    public Roadmap findRoadmapById(Long roadmapId) throws IllegalArgumentException {
        Roadmap roadmap;
        if (roadmapId == null) {
            throw new IllegalArgumentException("Roadmap must be provided");
        } else{
            roadmap = roadmapRepo.findById(roadmapId).orElseThrow(() -> new RuntimeException("Roadmap not found"));
            //this line gets the roadmap if exists. If not, throws exception
        }
        return roadmap;
    }

    public void deleteById(Long roadmapId) {
        Roadmap roadmap = roadmapRepo.findById(roadmapId).orElseThrow(() -> new RuntimeException("Roadmap not found"));
        roadmapRepo.delete(roadmap);
    }

    //Search roadmap by name
    public Roadmap findByName(String roadmapName) {
        Roadmap roadmap;
        if (roadmapName == null) {
            throw new IllegalArgumentException("Roadmap name must be provided");
        } else{
            roadmap = roadmapRepo.findByName(roadmapName).orElseThrow(() -> new RuntimeException("Roadmap not found"));
        }
        return roadmap;
    }

    public List<Roadmap> findAll(){
        return roadmapRepo.findAll();
    }
}
