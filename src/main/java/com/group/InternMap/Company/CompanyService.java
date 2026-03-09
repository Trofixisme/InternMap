package com.group.InternMap.Company;
import com.group.InternMap.Recruiter.RecruiterAddedEvent;
import com.group.InternMap.Recruiter.RecruiterService;
import com.group.InternMap.Recruiter.Recruiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
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

    @EventListener
    public void handleRecruiterAddedEvent(RecruiterAddedEvent event) {
        long recruiterId = event.recruiterId();
        long companyId = event.companyId();
        Recruiter recruiter = recruiterService.findRecruiterById(recruiterId);
        Company company = companyRepo.findCompanyById(companyId);
       // company.addRecruiter(recruiter);
        System.out.println("Company updated after recruiter was added.");
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
}