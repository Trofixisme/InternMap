package com.group.InternMap.Company;

import com.group.InternMap.Recruiter.RecruiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CompanyService {

    CompanyRepo companyRepo;
    RecruiterService recruiterService;

    @Autowired
    public CompanyService(CompanyRepo companyRepo, RecruiterService recruiterService) {
        this.companyRepo = companyRepo;
        this.recruiterService = recruiterService;
    }

    public Company findByName(String companyName){
        if (companyName == null || companyName.isBlank()) return null;
        return companyRepo.findCompanyByName(companyName);
    }

    public Company findCompanyById(Long companyId) throws IllegalArgumentException {
        if (companyId == null ) {
            throw new IllegalArgumentException("companyId must be provided");
        }
        return companyRepo.findCompanyById(companyId);
    }

    public List<Company> viewAllCompanies(){
        return companyRepo.findAll();
     }

    public void deleteCompany(Long companyId) {
        Company company = companyRepo.findCompanyById(companyId);

        if (company == null) {
            throw new IllegalArgumentException("Company not found");
        }

        companyRepo.delete(company);
    }

    public void save(Company company) {
        companyRepo.save(company);
    }
}