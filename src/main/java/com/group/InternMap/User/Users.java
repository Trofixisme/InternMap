package com.group.InternMap.User;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Users implements Serializable {

    private int role;

    @Column(nullable = false)
    private String plainPassword;

    @Column(nullable = false)
    private String fName;

    @Column(nullable = false)
    private String lName;

    @Column(nullable = false,unique = true)
    private String email;

    @Id @GeneratedValue
    private Long id;

    public Users() {}

    public Users(String fName, String lName, String email, String plainPassword) {
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.plainPassword = plainPassword;
    }

    public int getRole() {
        return role;
    }

    public Users setRole(Short role) {
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

    public void setEmail(String email) {
        //Returns a boolean value depending on whether the new email was set or not
        if (email.contains("@") && email.contains(".")) {
            this.email = email;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;

    }

    public void setRole(int role) {
        this.role = role;
    }
}
