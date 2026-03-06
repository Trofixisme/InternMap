package com.group.InternMap.Roadmap;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoadmapRepo extends JpaRepository<Roadmap, Long>{
    Optional<Roadmap> findByName(String roadmapName);

    Roadmap findRoadmapById(long id);

    //List<Application> searchByTerm(@Param("query") String query);

}
