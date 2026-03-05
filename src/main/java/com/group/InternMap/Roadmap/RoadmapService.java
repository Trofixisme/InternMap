package com.group.InternMap.Roadmap;

import org.springframework.stereotype.Service;

@Service
public class RoadmapService {
    RoadmapRepo roadmapRepo;

    public RoadmapService(RoadmapRepo roadmapRepo) {
        this.roadmapRepo = roadmapRepo;// without this , the controller will have empty data
    }

    Roadmap roadmap = new Roadmap(new RoadmapModule("software", " "));
    public Roadmap findRoadmapById(Long roadmapId) {
        if (roadmapId == null) {
            throw new IllegalArgumentException("Roadmap must be provided");
        } else{
            roadmap = roadmapRepo.findById(roadmapId).orElseThrow(() -> new RuntimeException("Roadmap not found"));
            //this line gets roadmap if exist , if not throw exception
        }
        return roadmap;
    }

    public void deleteById(Long roadmapId) {
        Roadmap roadmap = roadmapRepo.findById(roadmapId).orElseThrow(() -> new RuntimeException("Roadmap not found"));
        roadmapRepo.delete(roadmap);
    }

    //search roadmap by name
    public Roadmap findByName(String roadmapName) {
        Roadmap roadmap;
        if (roadmapName == null) {
            throw new IllegalArgumentException("Roadmap name must be provided");
        } else{
            roadmap = roadmapRepo.findByName(roadmapName).orElseThrow(() -> new RuntimeException("Roadmap not found"));
        }
        return roadmap;
    }

}
