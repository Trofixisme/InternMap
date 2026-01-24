package com.group.InternMap.Model.User.Company;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Company implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String industry;

    @Column(nullable = false)
    private String name;
    private String websiteURL;
    private String locationOfHQ;

    @ManyToMany(mappedBy = "companies")
    private List<Recruiter> recruiters = new ArrayList<>();

    public String getLocationOfHQ() {
        return locationOfHQ;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsiteURL() {
        return websiteURL;
    }

    public void setWebsiteURL(String websiteURL) {
            if (websiteURL == null || websiteURL.isBlank()) return;
            this.websiteURL = websiteURL;
    }

//    public void addRecruiter(Recruiter recruiter) {
//        if (recruiter == null) return;
//        if (!recruiters.contains(recruiter)) recruiters.add(recruiter);
//    }
//
//    public void removeRecruiter(Recruiter recruiter) {
//        recruiters.remove(recruiter);
//    }
}