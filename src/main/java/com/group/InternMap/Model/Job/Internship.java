package com.group.InternMap.Model.Job;

import com.group.InternMap.Model.User.Company.Company;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Internship extends JobPosting {

    private int duration;

    @ManyToOne @JoinColumn(name = "companyID", referencedColumnName = "id")
    private Company company;

    public int getDuration() {
        return duration;
    }
}