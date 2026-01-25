package com.group.InternMap.Model.Roadmap.Skill;

import com.group.InternMap.Model.Roadmap.Status;
import com.group.InternMap.Model.User.Student;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

@Entity
public class UserSkillStatus implements Serializable {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne @JoinColumn(name = "skill_id")
    private Skill skill;

    @ManyToOne @JoinColumn(name = "student_id")
    private Student student;

    @Enumerated
    private Status status;

    private Date lastModified;

    public UserSkillStatus(Skill skill, Student student, Status status) {
        this.skill = skill;
        this.student = student;
        this.status = status;
    }

    public UserSkillStatus(Skill skill, Student student) {
        this(skill, student, Status.NOT_STARTED);
    }

    public UserSkillStatus() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
        updateLastModified();
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setStatus(Status status) {
        this.status = status;
        updateLastModified();
    }

    public Status getStatus() {
        return status;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void updateLastModified() {
        lastModified = Date.from(Instant.now());
    }
}
