package com.group.InternMap.Job;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
public class Internship extends JobPosting implements Serializable {

    private int duration;
    private String jobLocation;

    public int getDuration() {
        return duration;
    }

    public String getJobLocation() {
        return jobLocation;
    }
}