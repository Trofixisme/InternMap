package com.group.InternMap.Application;

import com.group.InternMap.Student.Student;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;

@Entity
public class CV implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cvId;

    private String description;
    private String pastExperiences;
    private String projects;

    public CV() {}

    public CV(Student student, String description, String pastExperiences, String projects) {
        super();
        this.description = description;
        this.pastExperiences = pastExperiences;
        this.projects = projects;
    }

    public String getPastExperiences() {
        return pastExperiences;
    }

    public void setPastExperiences(String pastExperiences) {
        this.pastExperiences = pastExperiences;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCvId() {
        return cvId;
    }

    public String getProjects() {
        return projects;
    }

    public void setProjects(String projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        return "CV{" +
                "cvId=" + cvId +
                ", description='" + description + '\'' +
                ", pastExperiences='" + pastExperiences + '\'' +
                ", projects='" + projects + '\'' +
                '}';
    }
}
