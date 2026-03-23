package com.group.InternMap.Job;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class FullTime extends JobPosting implements Serializable {

    @Column(nullable = false)
    private String benefits;

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }
}