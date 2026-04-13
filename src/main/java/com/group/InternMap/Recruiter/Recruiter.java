package com.group.InternMap.Recruiter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.group.InternMap.Job.JobPosting;
import com.group.InternMap.Company.Company;
import com.group.InternMap.User.UserRole;
import com.group.InternMap.User.Users;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class Recruiter extends Users implements Serializable {

    @Column(nullable = false)
    private String title;

//    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    //@JoinTable(name = "recruiters_companies", joinColumns = @JoinColumn(name = "recruiter_id"), inverseJoinColumns = @JoinColumn(name = "company_id"))
    @ManyToMany
    private List<Company> companies = new ArrayList<>();

    public Recruiter() {}

    public Recruiter(String fName, String lName, String email, String plainPassword, String title, List<Company> companies, Collection<JobPosting> jobPosting) {
        super(fName, lName, email, plainPassword);
        setRole(UserRole.RECRUITER);
        this.title = title;
        this.companies = companies;
        this.jobPosting = jobPosting;
    }

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
//    @JsonIgnore
    private Collection<JobPosting> jobPosting;

    public Collection<JobPosting> getJobPosting() {
        return jobPosting;
    }

    public void setJobPosting(Collection<JobPosting> jobPosting) {
        this.jobPosting = jobPosting;
    }



}