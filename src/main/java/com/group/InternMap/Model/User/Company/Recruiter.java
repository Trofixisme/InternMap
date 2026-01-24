package com.group.InternMap.Model.User.Company;

import com.group.InternMap.Model.Job.JobPosting;
import com.group.InternMap.Model.User.User;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class Recruiter extends User implements Serializable {

    @Column(nullable = false)
    private String title;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "recruiters_companies", joinColumns = @JoinColumn(name = "recruiter_id"), inverseJoinColumns = @JoinColumn(name = "company_id"))
    private List<Company> companies = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addCompany(Company company) {
        companies.add(company);
    }

    public void removeCompany(Company company) {
        companies.remove(company);
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }

    @OneToMany(mappedBy = "recruiter")
    private Collection<JobPosting> jobPosting;

    public Collection<JobPosting> getJobPosting() {
        return jobPosting;
    }

    public void setJobPosting(Collection<JobPosting> jobPosting) {
        this.jobPosting = jobPosting;
    }
}