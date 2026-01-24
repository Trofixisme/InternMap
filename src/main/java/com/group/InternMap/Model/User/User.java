package com.group.InternMap.Model.User;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Users")
public class User {

    @NotNull
    private String plainPassword;
    @NotNull
    private String fName;
    @NotNull
    private String lName;
    @NotNull
    private String email;

    @Id
    private Long id;

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
