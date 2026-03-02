package com.group.InternMap.User;

import java.io.Serializable;

public enum PermissionLevel implements Serializable {
    
    LOW(0),
    MEDIUM(1),
    HIGH(2),
    COMPLETE_ACCESS(3);

    final int ID;

    PermissionLevel(int id) {
        ID = id;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
