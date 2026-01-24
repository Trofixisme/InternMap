//Created by Ziad on 30/10/2025

package com.group.InternMap.Model.Roadmap;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.*;

@Entity
public final class Roadmap implements Serializable {
    @Id
    private final UUID roadmapID;
    private String name;
    private final ArrayList<RoadmapModule> roadmapModules = new ArrayList<>();

    public Roadmap() {
        this.roadmapID = UUID.randomUUID();
    }

    public Roadmap(String roadmapID, String name, RoadmapModule... modules) {
        this.roadmapID = UUID.fromString(roadmapID);
        this.name = name;

        if (modules != null)
            addModules(modules);
    }

    public Roadmap(String name, RoadmapModule... modules) {
        roadmapID = UUID.randomUUID();
        this.name = name;

        if (modules != null)
            addModules(modules);
    }

    public Roadmap(String name) {
        this(name, null);
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        name = newName;
    }

    public String getRoadmapID() {
        return roadmapID.toString();
    }

   public void addModules(RoadmapModule... modules) {
        roadmapModules.addAll(List.of(modules));
    }

    public ArrayList<RoadmapModule> getAllModules() {
        return roadmapModules;
    }

    @Override
    public String toString() {
        return  roadmapID + "|" +
                name + "|" +
                roadmapModules.toString()  + "|";
    }

}
