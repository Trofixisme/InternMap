package com.group.InternMap.Model.User;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)

public class User implements Serializable {

    @Column(name = "role")
    private Short role;

    @Column(nullable = false)
    private String plainPassword;

    @Column(nullable = false)
    private String fName;

    @Column(nullable = false)
    private String lName;

    @Column(nullable = false)
    private String email;

    @Id @GeneratedValue
    private Long id;

    public User() {}

    public User(String fName, String lName, String email, String plainPassword) {
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.plainPassword = plainPassword;
    }

    public Short getRole() {
        return role;
    }

    public User setRole(Short role) {
        this.role = role;
        return this;
    }

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
      this.plainPassword = plainPassword;
    }

    public String getFName() {
        return fName;
    }

    public void setFName(String fName) {
        //TODO: Add some real validation
        this.fName = fName;
    }

    public String getLName() {
        return lName;
    }

    public void setLName(String lName) {
        //TODO: Add some real validation to this one too
        this.lName = lName;
    }

    public String getEmail() {
        return email;
    }

    public boolean setEmail(String email) {
        //Returns a boolean value depending on whether the new email was set or not
        if (email.contains("@") && email.contains(".")) {
            this.email = email;
            return true;
        } else {
            return false;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
