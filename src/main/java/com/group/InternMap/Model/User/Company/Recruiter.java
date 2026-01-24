package com.group.InternMap.Model.User.Company;

import com.group.InternMap.Model.User.User;
import com.group.InternMap.Model.User.UserRole;
import jakarta.persistence.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
@Entity
public class Recruiter extends User implements Serializable {
    private String title;
    private ArrayList<Company> companies = new ArrayList<>();

    public Recruiter() {
        super();
        this.role = UserRole.RECRUITER;
    }

    public Recruiter(String fName, String lName, String email, String plainPassword, UserRole role, String title) {
        super(fName, lName, email, plainPassword,role);
        this.title = title;
    }

    public Recruiter(String recruiterID, String fName, String lName, String email, String plainPassword, UserRole role,String title) {
        super(recruiterID, fName, lName, email, plainPassword,role);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addCompany(Company company) {
        companies.add(company);
    }

    public void removeCompany(Company company) {
        companies.remove(company);
    }

    public ArrayList<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(ArrayList<Company> companies) {
        this.companies = companies;
    }

    @Override
    public String toString() {
        return "Recruiter{" +
                "id=" + getUserID() +
                ", companyIds=" + (companies != null && !companies.isEmpty() ?
                companies.stream()
                        .filter(Objects::nonNull) // Filter out any null companies
                        .map(Company::getCompanyID)
                        .toList() :
                "[]") +
                "}";
    }
}