package com.group.InternMap.Model.User;

import java.io.Serializable;
import java.util.UUID;

public class CV implements Serializable {

    private UUID cvId;
    private Student student;
    private String description;
    private String pastExperiences;
    private String projects;

    public CV() {
        this.cvId = UUID.randomUUID();
    }

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

    public UUID getCvId() {
        return cvId;
    }

    public String getProjects() {
        return projects;
    }

    public void setProjects(String projects) {
        this.projects = projects;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "CV{" +
                "cvId=" + cvId +
                ", student=" + student +
                ", description='" + description + '\'' +
                ", pastExperiences='" + pastExperiences + '\'' +
                ", projects='" + projects + '\'' +
                '}';
    }
}
