package com.group.InternMap.Job;

import java.io.Serializable;

public enum PostingType implements Serializable {

    Internship(1),
    FullTime(2),
    FreeLanceProject(3);

    private final int id;

    PostingType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
