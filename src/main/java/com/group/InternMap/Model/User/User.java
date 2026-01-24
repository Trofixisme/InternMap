package com.group.InternMap.Model.User;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Users")
public class User implements Serializable {

    @Column(nullable = false)
    private String plainPassword;
    @Column(nullable = false)
    private String fName;
    @Column(nullable = false)
    private String lName;
    @Column(nullable = false)
    private String email;

    @Id
    private Long id;

    public User() {
    }

    public User(Long id, String fName, String lName, String email, String plainPassword) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.plainPassword = plainPassword;
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
        this.fName = fName;
    }

    public String getLName() {
        return lName;
    }

    public void setLName(String lName) {
        this.lName = lName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
//        if (email.contains("@") && email.contains(".")) {
//            this.email = email;
//        } else {
//            //I don't think we should be throwing errors in servers,
//            throw new IllegalArgumentException("Provided email isn't valid");
//        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
