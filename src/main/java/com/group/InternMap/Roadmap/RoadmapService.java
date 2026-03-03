package com.group.InternMap.Roadmap;

import com.group.InternMap.Deprecated.Repository.RepositoryAccessors;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RoadmapService {
    RoadmapRepo roadmapRepo;

    public RoadmapService(RoadmapRepo roadmapRepo) {
        this.roadmapRepo = roadmapRepo;// without this , the controller will have empty data
    }

    Roadmap roadmap = new Roadmap();
    public Roadmap findRoadmapById(Long roadmapId) {
        if (roadmapId == null) {
            throw new IllegalArgumentException("Roadmap must be provided");
        } else{
            roadmap = roadmapRepo.findById(roadmapId).orElseThrow(() -> new RuntimeException("Roadmap not found"));
            //this line gets roadmap if exist , if not throw exception
        }
        return roadmap;
//        return RepositoryAccessors.allRoadmaps.stream()
//                .filter(r -> r.getId().equals(roadmapId.toString()))
//                .findFirst()
//                .orElseThrow(() -> new RuntimeException("Roadmap not found: " + roadmapId));
    }

    public void deleteById(Long roadmapId) {
        Roadmap roadmap = roadmapRepo.findById(roadmapId).orElseThrow(() -> new RuntimeException("Roadmap not found"));
        roadmapRepo.delete(roadmap);
          //RepositoryAccessors.allRoadmaps.removeIf(r -> r.getId().equals(id.toString()));
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
