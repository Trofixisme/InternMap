package com.group.InternMap.Model.Job;

import com.group.InternMap.Model.User.Company.Company;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class FullTime extends JobPosting {

    @Column(nullable = false)
    private String benefits;

    @ManyToOne @JoinColumn(name = "companyID", referencedColumnName = "id")
    private Company company;

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }
}