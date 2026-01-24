//Created by Ziad on 28/10/2025

package com.group.InternMap.Model.User;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.UUID;
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Users")
public class User implements Serializable {

    private String plainPassword;
    private String fname;
    private String lname;
    private String email;
    @Id
    private final UUID userID;
    protected UserRole role;

    public User() {
        userID = UUID.randomUUID();
    }

    public User(String fname, String lname, String email, String plainPassword, UserRole role) {
        userID = UUID.randomUUID();
        this.plainPassword=plainPassword;
        setFname(fname);
        setLname(lname);
        setEmail(email);
        this.role = role;
    }
    public User(String userID, String fname, String lname, String email, String plainPassword, UserRole role) {
        this.userID = UUID.fromString(userID);
        this.plainPassword=plainPassword;
        setFname(fname);
        setLname(lname);
        setEmail(email);
        this.role = role;
    }
    public String getPlainPassword() {
        return plainPassword;
    }
    public void setPlainPassword(String plainPassword) {
      this.plainPassword = plainPassword;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public UUID getUserID() {
        return userID;
    }

    public void setEmail(String email) {
        this.email = email;
//        if (email.contains("@") && email.contains(".")) {
//            this.email = email;
//        } else {
//            //I don't think we should be throwing errors in the servers,
//            throw new IllegalArgumentException("Provided email isn't valid");
//        }
    }
    public UserRole getRole() {
        return role;
    }

    @Override
    public String toString() {
        return  getUserID() + "|" +
                getFname() + "|" +
                getLname() + "|" +
                getEmail() + "|" +
                getPlainPassword() + "|" +
                getRole() + "|";
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof User)) return false;

        if (this == o) return true;
        return this.getEmail().equalsIgnoreCase(((User) o).getEmail());
    }
}
