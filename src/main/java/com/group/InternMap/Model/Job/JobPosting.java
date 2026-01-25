package com.group.InternMap.Model.Job;

import com.group.InternMap.Model.User.Application;
import com.group.InternMap.Model.User.Company.Recruiter;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

@Entity
public class JobPosting implements Serializable {

    @Id @GeneratedValue
    private Long id;
    private String jobDescription;

    @Column(nullable = false)
    private Date datePosted = Date.from(Instant.now());
    private String jobRequirements;

    @Column(nullable = false)
    private String jobName;

//    @ManyToOne @JoinColumn(name = "recruiter_id", referencedColumnName = "id")
//    private Recruiter recruiter;
@ManyToOne
@JoinColumn(name = "recruiter_id")
private Recruiter recruiter;

    @OneToMany @JoinColumn(name = "application_id", referencedColumnName = "id")
    private ArrayList<Application> applications = new ArrayList<>();

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public void setDatePosted(Date datePosted) {
        this.datePosted = datePosted;
    }

    public void setJobRequirements(String jobRequirements) {
        this.jobRequirements = jobRequirements;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Date getPostingDate() {
        return datePosted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public String getJobRequirements() {
        return jobRequirements;
    }

    public String getJobName() {
        return jobName;
    }

    public Recruiter getRecruiter() {
        return recruiter;
    }

    public String getRecruiterEmail() {
        if (recruiter == null) {
            return "null";
        }
        return recruiter.getEmail();
    }

    public void setRecruiterEmail(String recruiterEmail) {
        if (recruiter == null) {
            recruiter.setEmail(recruiterEmail);
        }
    }

    public void addApplication(Application application) {
        this.applications.add(application);
    }

    public void setRecruiter(Recruiter recruiter) {
        this.recruiter = recruiter;
    }

    public ArrayList<Application> getApplication() {
        return applications;
    }

    public void deleteApplication(Application application) {
        this.applications.remove(application);
    }

    public void setApplication(ArrayList<Application> application) {
        this.applications = application;
    }
}