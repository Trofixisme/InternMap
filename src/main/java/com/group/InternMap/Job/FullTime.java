package com.group.InternMap.Job;

import com.group.InternMap.Company.Company;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class FullTime extends JobPosting implements Serializable {

    @Column(nullable = false)
    private String benefits;

    @ManyToOne //@JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }
}