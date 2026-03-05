package com.group.InternMap.Roadmap;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.*;

@Entity
public final class Roadmap implements Serializable {

    @Id @GeneratedValue
    private Long id;
    private String name;

    public Roadmap() {
    }

    @ManyToMany
    //@JoinTable(name = "roadmaps_modules", joinColumns = @JoinColumn(name = "roadmap_id"), inverseJoinColumns = @JoinColumn(name = "roadmap_module_id"))
    private List<RoadmapModule> roadmapModules = new ArrayList<>();

    public Roadmap(RoadmapModule name, Object o) {}


    public Roadmap(String name, RoadmapModule... modules) {
        this.name = name;

        if (modules != null)
            addModules(modules);
    }

    public Roadmap(RoadmapModule name) {
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

    public List<RoadmapModule> getAllModules() {
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
