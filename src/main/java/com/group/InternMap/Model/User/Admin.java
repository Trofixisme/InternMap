package com.group.InternMap.Model.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;

import java.io.Serializable;

@Entity
public class Admin extends User implements Serializable {

    @Enumerated
    @Column(nullable = false)
    PermissionLevel permissionLevel = PermissionLevel.LOW;

    public Admin(){
        super();
    }

    public Admin(String fName, String lName, String email, String plainPassword) {
        super(fName, lName, email, plainPassword);
    }
}

