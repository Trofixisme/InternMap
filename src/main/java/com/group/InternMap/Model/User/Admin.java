package com.group.InternMap.Model.User;

import jakarta.persistence.Entity;

import java.io.Serializable;
@Entity
public class Admin extends User implements Serializable {

    public Admin(){
        super();
    }

    public Admin(Long id, String fName, String lName, String email, String plainPassword) {
        super(id,fName,lName, email, plainPassword);
    }
}

