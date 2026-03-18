package com.group.InternMap.Job;

import com.group.InternMap.Application.Application;
import com.group.InternMap.Company.Company;
import com.group.InternMap.Recruiter.Recruiter;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class JobPosting implements Serializable {

    @Id @GeneratedValue
    private Long id;
    private String jobDescription;

    @Column(nullable = false)
    private Date datePosted = Date.from(Instant.now());
    private String jobRequirements;

    @Column(nullable = false)
    private String jobName;

    @ManyToOne
    private Recruiter recruiter;

    @ManyToOne
    private Company company;

    @OneToMany(mappedBy = "jobPosting")
    private List<Application> applications;

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
        if (recruiter != null) {
            recruiter.setEmail(recruiterEmail);
        }
    }



    public void setRecruiter(Recruiter recruiter) {
        this.recruiter = recruiter;
    }

    public List<Application> getApplication() {
        return applications;
    }

    public void deleteApplication(Application application) {
        this.applications.remove(application);
    }

    public void setApplication(ArrayList<Application> application) {
        this.applications = application;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public void setCompanyName(String companyName) {
         company.setName(companyName);
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }
    public void addApplication(Application application) {
        this.applications.add(application);
    }

    public List<Application> getApplications() {
        return applications;
    }
}