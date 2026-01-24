package com.group.InternMap.Model.Job;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class FreeLanceProject extends JobPosting {

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
