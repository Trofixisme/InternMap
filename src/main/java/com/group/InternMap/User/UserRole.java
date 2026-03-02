package com.group.InternMap.User;

import java.io.Serializable;

public enum UserRole implements Serializable {

    STUDENT(1),
    RECRUITER(2),
    ADMIN(3);

    private final int ID;

    UserRole(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }
}
