//Created by Ziad on 30/10/2025

package com.group.InternMap.Model.Roadmap;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.*;

@Entity
public final class Roadmap implements Serializable {
    @Id @GeneratedValue
    private long id;
    private String name;
    private final ArrayList<RoadmapModule> roadmapModules = new ArrayList<>();

    public Roadmap() {

    }

    public Roadmap(String roadmapID, String name, RoadmapModule... modules) {
        this.name = name;
        if (modules != null)
            addModules(modules);
    }

    public Roadmap(String name, RoadmapModule... modules) {
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

    public long getId() {
        return id;
    }

   public void addModules(RoadmapModule... modules) {
        roadmapModules.addAll(List.of(modules));
    }

    public ArrayList<RoadmapModule> getAllModules() {
        return roadmapModules;
    }

    @Override
    public String toString() {
        return  id + "|" +
                name + "|" +
                roadmapModules.toString()  + "|";
    }

}
