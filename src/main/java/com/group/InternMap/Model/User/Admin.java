package com.group.InternMap.Model.User;

import java.io.Serializable;

public class Admin extends User implements Serializable {

    public Admin(){
        super();
        this.role = UserRole.ADMIN;
    }

    public Admin(String fname, String lname, String email, String plainPassword, UserRole role) {
        super(fname, lname, email, plainPassword, role);
    }
}

