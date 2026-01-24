package com.group.InternMap.Model.Job;

import com.group.InternMap.Model.User.Company.Company;
import jakarta.persistence.*;

@Entity
public class FullTime extends JobPosting {

    private String benefits;

    @ManyToOne //@JoinColumn(name = "companyID", referencedColumnName = "id")
    private Company company;

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }
}