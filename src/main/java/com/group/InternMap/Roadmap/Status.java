package com.group.InternMap.Roadmap;

import java.io.Serializable;

public enum Status implements Serializable {
    IN_PROGRESS(0), NOT_STARTED(1), DONE(2), SKIPPED(3);

    private final int code;

    Status(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
