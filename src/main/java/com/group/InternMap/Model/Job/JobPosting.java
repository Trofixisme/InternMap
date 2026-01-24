package com.group.InternMap.Model.Job;

import com.group.InternMap.Model.User.Application;
import com.group.InternMap.Model.User.Company.Recruiter;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

@Entity
public class JobPosting {

    @Id
    private Long id;
    private String jobDescription;
    private Date datePosted = Date.from(Instant.now());
    private String jobRequirements;
    private String jobName;
    private String companyName;

    @ManyToOne @JoinColumn(name = "recruiter_id")
    private Recruiter recruiter;

    private ArrayList<Application> application = new ArrayList<>();

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

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Date getDatePosted() {
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

    public void setApplication(Application application) {
        this.application.add(application);
    }

    public ArrayList<Application> viewApplications() {
       return application;
    }

    public void setRecruiter(Recruiter recruiter) {
        this.recruiter = recruiter;
    }

    public String getCompanyName() {return  companyName;}
    public ArrayList<Application> getApplication() {
        return application;
    }

    public void deleteApplication(Application application) {
        this.application.remove(application);
    }

    public void setApplication(ArrayList<Application> application) {
        this.application = application;
    }
}