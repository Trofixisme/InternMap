package com.group.InternMap.Company;
import com.group.InternMap.Recruiter.RecruiterAddedEvent;
import com.group.InternMap.Recruiter.RecruiterService;
import com.group.InternMap.User.Users;
import com.group.InternMap.Recruiter.Recruiter;
import com.group.InternMap.Deprecated.Repository.BaseRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import java.util.List;
import static com.group.InternMap.FilePaths.userPath;

@Service

public class CompanyService {
    static CompanyRepo companyRepo; // idk why I made this static bs it is for the find myname is also static so idk ( ZIAD HELP )
    protected final BaseRepository<Users> repo = new BaseRepository<>(Users.class, userPath);
    public RecruiterService recruiterService;

    CompanyService(RecruiterService recruiterService) {
        this.recruiterService = recruiterService;
    }

    @EventListener
    public void handleRecruiterAddedEvent(RecruiterAddedEvent event) {
        long recruiterId = event.getRecruiterId();
        long companyId = event.getCompanyId();
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