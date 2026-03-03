package com.group.InternMap.Roadmap;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoadmapRepo extends JpaRepository<Roadmap, Long>{
    Optional<Roadmap> findByName(String roadmapName);
    //Optional<Roadmap> getById(long roadmapId);


    //List<Application> searchByTerm(@Param("query") String query);

}
