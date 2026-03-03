package com.group.InternMap.Admin;

import com.group.InternMap.User.PermissionLevel;
import com.group.InternMap.User.UserRole;
import com.group.InternMap.User.Users;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;

import java.io.Serializable;

@Entity
public class Admin extends Users implements Serializable {

    @Enumerated
    @Column(nullable = false)
    PermissionLevel permissionLevel = PermissionLevel.LOW;

    public Admin(){
        super();
    }

    public Admin(int Role,String fName, String lName, String email, String plainPassword) {
        super(fName, lName, email, plainPassword);
        setRole(UserRole.ADMIN.getID());
    }
}