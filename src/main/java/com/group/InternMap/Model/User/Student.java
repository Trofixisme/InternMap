package com.group.InternMap.Model.User;

import jakarta.persistence.Entity;

import java.io.Serializable;
import java.util.Calendar;

@Entity
public class Student extends User implements Serializable {

    private int graduatingYear = Calendar.getInstance().get(Calendar.YEAR) + 1;
    private String uniName;
    private String studentMajor;
    private String faculty;

    public Student(String fName, String lName, String email, String plainPassword, int graduatingYear, String uniName, String studentMajor, String faculty) {
        super(fName, lName, email, plainPassword);
        this.graduatingYear = graduatingYear;
        this.uniName = uniName;
        this.studentMajor = studentMajor;
        this.faculty = faculty;
    }

    public Student() {}

    public int getGraduatingYear() {
        return graduatingYear;
    }

    public void setGraduatingYear(int graduatingYear) {
        this.graduatingYear = graduatingYear;
    }

    public String getUniName() {
        return uniName;
    }

    public void setUniName(String uniName) {
        this.uniName = uniName;
    }

    public String getStudentMajor() {
        return studentMajor;
    }

    public void setStudentMajor(String studentMajor) {
        this.studentMajor = studentMajor;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }
}