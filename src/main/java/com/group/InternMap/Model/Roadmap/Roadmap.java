package com.group.InternMap.Model.Roadmap;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.*;

@Entity
public final class Roadmap implements Serializable {

    @Id @GeneratedValue
    private Long id;
    private String name;

    //TODO: Revise this relationship and correct it if needed
    @OneToMany @JoinColumn(name = "roadmap_id")
    private ArrayList<RoadmapModule> roadmapModules = new ArrayList<>();

    public Roadmap() {}

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

   public void addModules(RoadmapModule... modules) {
        roadmapModules.addAll(List.of(modules));
    }

    public ArrayList<RoadmapModule> getAllModules() {
        return roadmapModules;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name + "|" + roadmapModules.toString()  + "|";
    }
}
