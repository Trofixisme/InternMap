package com.group.InternMap.Model.Roadmap.Skill;

import com.group.InternMap.Repo.Deprecated.RoadmapProgression;
import com.group.InternMap.Model.Roadmap.Status;
import com.group.InternMap.Model.User.Student;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

public class UserSkillStatus implements Serializable {

    private Skill skill;
    private Student student;

    private Status status;
    private Date lastModified = Date.from(Instant.now());

    protected RoadmapProgression roadmapProgressionDelegate;

    //MARK: Constructors
    public UserSkillStatus(Skill skill, Student student, Status status) {
        this.skill = skill;
        this.student = student;
        this.status = status;
    }

    public UserSkillStatus(Skill skill, Student student) {
        this(skill, student, Status.NOT_STARTED);
    }

    //MARK: Getters and Setters
    public Date getLastModified() { return lastModified; }

    public void setLastModified(Date newLastModifiedTime) {
        if (newLastModifiedTime.getTime() < lastModified.getTime()) {
         throw new RuntimeException("You cannot change the last modified date to a time that is before the current last modified date");
        } else {
            lastModified = newLastModifiedTime;
        }
    }

    public void setStatus(Status status) {
        this.status = status;
        if (roadmapProgressionDelegate != null) {
            roadmapProgressionDelegate.updateCompletionPercentage();
        }
    }

    public Status getStatus() { return status; }

    //MARK: Methods
    public void setDelegate(RoadmapProgression roadmapProgressionDelegate) {
        this.roadmapProgressionDelegate = roadmapProgressionDelegate;
    }
}
