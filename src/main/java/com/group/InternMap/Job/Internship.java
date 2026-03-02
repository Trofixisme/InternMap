package com.group.InternMap.Job;

import com.group.InternMap.Company.Company;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Internship extends JobPosting implements Serializable {

    private int duration;

    @ManyToOne //@JoinColumn(name = "companyID", referencedColumnName = "id")
    private Company company;

    public int getDuration() {
        return duration;
    }
}