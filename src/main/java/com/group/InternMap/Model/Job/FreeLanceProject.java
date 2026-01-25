package com.group.InternMap.Model.Job;

import jakarta.persistence.Entity;
import java.io.Serializable;

@Entity
public class FreeLanceProject extends JobPosting implements Serializable {

    private double Duration;
    private int Payout;
    private String jobLocation;

    public double getDuration() {
        return Duration;
    }

    public int getPayout() {
        return Payout;
    }
}
