package com.group.InternMap.Model.User;

import jakarta.persistence.Entity;

import java.io.Serializable;
@Entity
public class Admin extends User implements Serializable {

    public  Admin(){
        super();
        this.role = UserRole.ADMIN;
    }

    public Admin(String fname, String lname, String email, String plainPassword, UserRole role) {
        super(fname, lname, email, plainPassword, role);
    }
}

