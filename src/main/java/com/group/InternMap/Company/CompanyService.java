package com.group.InternMap.Company;
import com.group.InternMap.Recruiter.RecruiterAddedEvent;
import com.group.InternMap.Recruiter.RecruiterService;
import com.group.InternMap.User.Users;
import com.group.InternMap.Recruiter.Recruiter;
import com.group.InternMap.Deprecated.Repository.BaseRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import java.util.List;


@Service

public class CompanyService {
    static CompanyRepo companyRepo; // idk why I made this static bs it is for the find myname is also static so idk ( ZIAD HELP )
    public RecruiterService recruiterService;

    @EventListener
    public void handleRecruiterAddedEvent(RecruiterAddedEvent event) {
        long recruiterId = event.recruiterId();
        long companyId = event.companyId();
        Recruiter recruiter =recruiterService.findRecruiterById(recruiterId);
        Company company = companyRepo.findCompanyById(companyId);
       // company.addRecruiter(recruiter);
        System.out.println("Company updated after recruiter was added.");
    }

    public Company findByName(String companyName)  {
        if (companyName == null || companyName.isBlank()) return null;
        return companyRepo.findCompanyByName(companyName);
    }

    public Company findCompanyById(Long companyId) {
        if (companyId == null ) {
            throw new IllegalArgumentException("companyId must be provided");
        }
        return companyRepo.findCompanyById(companyId);
    }

    public List<Company> viewAllCompanies() throws Exception {
        return companyRepo.findAll();
     }
}