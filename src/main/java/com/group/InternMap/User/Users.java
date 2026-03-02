package com.group.InternMap.User;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Users implements Serializable {

    private Short role;

    @Column(nullable = false)
    private String plainPassword;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Id @GeneratedValue
    private Long id;

    public Users() {}

    public Users(String firstName, String lastName, String email, String plainPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.plainPassword = plainPassword;
    }

    public Short getRole() {
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
        return firstName;
    }

    public void setFName(String fName) {
        //TODO: Add some real validation
        this.firstName = fName;
    }

    public String getLName() {
        return lastName;
    }

    public void setLName(String lName) {
        //TODO: Add some real validation to this one too
        this.lastName = lName;
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
}
