package com.group.InternMap.DTO;

import com.group.InternMap.Roadmap.Roadmap;
import com.group.InternMap.Roadmap.RoadmapModule;
import com.group.InternMap.Skill.Skill;
import java.util.ArrayList;
import java.util.List;

public class RoadmapModuleSkill {
    private String roadmapName;
    private List<ModuleData> modules = new ArrayList<>();

    // Constructor
    public RoadmapModuleSkill() {
    }

    // Getters and Setters
    public String getRoadmapName() {
        return roadmapName;
    }

    public void setRoadmapName(String roadmapName) {
        this.roadmapName = roadmapName;
    }

    public List<ModuleData> getModules() {
        return modules;
    }

    public void setModules(List<ModuleData> modules) {
        this.modules = modules;
    }

    // Convert to actual models
    public Roadmap toRoadmap() {
        Roadmap roadmap = new Roadmap(this.roadmapName);

        for (ModuleData moduleData : this.modules) {
            RoadmapModule module = new RoadmapModule(
                    moduleData.getName(),
                    moduleData.getDescription()
            );

            for (SkillData skillData : moduleData.getSkills()) {
                List<String> urls = new ArrayList<>();
                for (String link : skillData.getLinks()) {
                    if (link != null) {
                        urls.add(link);
                    }
                }
                Skill skill = new Skill(
                        skillData.getName(),
                        skillData.getDescription(),
                        urls
                );
                module.addSkills(skill);
            }
            roadmap.addModules(module);
        }

        return roadmap;
    }

    // Inner class for Module data
    public static class ModuleData {
        private String name;
        private String description;
        private List<SkillData> skills = new ArrayList<>();

        public ModuleData() {}

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<SkillData> getSkills() {
            return skills;
        }

        public void setSkills(List<SkillData> skills) {
            this.skills = skills;
        }
    }

    // Inner class for Skill data
    public static class SkillData {
        private String name;
        private String description;
        private List<String> links = new ArrayList<>();

        public SkillData() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<String> getLinks() {
            return links;
        }

        public void setLinks(List<String> links) {
            this.links = links;
        }
    }
}