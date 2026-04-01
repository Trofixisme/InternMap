package com.group.InternMap.Application;

import java.io.Serializable;

public enum ApplicationStatus implements Serializable {

    Accepted(1),
    Rejected(2),
    InProgress(3);

    private final int id;

    ApplicationStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
