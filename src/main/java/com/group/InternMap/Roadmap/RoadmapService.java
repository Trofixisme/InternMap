package com.group.InternMap.Roadmap;

import com.group.InternMap.Deprecated.Repository.RepositoryAccessors;

import java.util.UUID;

public class RoadmapService {
    public Roadmap findRoadmapbyId(UUID roadmapId) {
        if (roadmapId == null) {
            throw new IllegalArgumentException("Roadmap must be provided");
        }

        return RepositoryAccessors.allRoadmaps.stream()
                .filter(r -> r.getId().equals(roadmapId.toString()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Roadmap not found: " + roadmapId));

    }

    public void deleteById(UUID id) {
          RepositoryAccessors.allRoadmaps.removeIf(r -> r.getId().equals(id.toString()));
    }
}
