package com.group.InternMap.Skill;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Skill implements Serializable {

    @Id @GeneratedValue
    private Long id;

    private String name;

    @ElementCollection
    private final List<String> resourceLinks = new ArrayList<>();

    private String description;

    public Skill(String name, String description, List<String> links) {
        this.name = name;
        this.description = description;

        if (links != null) {
            this.resourceLinks.addAll(links);
        }
    }

    public Skill(String name, String description){
        this.name=name;
        this.description = description;
    }

    public Skill() {}

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        name = newName;
    }

    public void addURLs(String... resourceLinks) {
        this.resourceLinks.addAll(List.of(resourceLinks));
    }

    public List<String> getResourceLinks() {
        return resourceLinks;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return name + '|' + resourceLinks + '|' + description + '|';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
