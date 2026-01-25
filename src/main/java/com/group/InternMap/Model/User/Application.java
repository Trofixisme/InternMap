package com.group.InternMap.Model.User;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

@Entity
public class Application implements Serializable, Comparable<Application> {

    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String fName;

    @Column(nullable = false)
    private String lName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private Date applicationDate = Date.from(Instant.now());

    @ManyToOne @JoinColumn(name = "student_id", referencedColumnName = "id")
    Student student;

    public Application() {}

    public Application(String fName, String lName, String email, String phoneNumber) {
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.applicationDate = new Date();
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getFName() {
        return fName;
    }

    public void setFName(String fName) {
        this.fName = fName;
    }

    public String getLName() {
        return lName;
    }

    public void setLName(String lName) {
        this.lName = lName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int compareTo(Application application) {
        if(this.getApplicationDate().before(application.getApplicationDate())){
            return 1;
        }
        else if(this.getApplicationDate().after(application.getApplicationDate())){
            return -1;
        }
        return 0;
    }
}