package com.group.InternMap.User;

public enum UserRole {

    STUDENT("ROLE_STUDENT", 1),
    RECRUITER("ROLE_RECRUITER", 2),
    ADMIN("ROLE_ADMIN", 3);

    private final String roleName;
    private final int id;

    UserRole(String roleName, int id) {
        this.roleName = roleName;
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public int getID() {
        return id;
    }
}
