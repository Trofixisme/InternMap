package com.group.InternMap.Company;

import com.group.InternMap.Recruiter.RecruiterAddedEvent;
import com.group.InternMap.Recruiter.RecruiterService;
import com.group.InternMap.User.Users;
import com.group.InternMap.Recruiter.Recruiter;
import com.group.InternMap.Deprecated.Repository.BaseRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import static com.group.InternMap.Deprecated.Repository.RepositoryAccessors.allCompanies;
import static com.group.InternMap.FilePaths.userPath;

@Service

public class CompanyService {

    protected final BaseRepository<Users> repo = new BaseRepository<>(Users.class, userPath);
    public RecruiterService recruiterService;

    CompanyService(RecruiterService recruiterService) {
        this.recruiterService = recruiterService;
    }

    @EventListener
    public void handleRecruiterAddedEvent(RecruiterAddedEvent event) {
        long recruiterId = event.getRecruiterId();
        String companyId = event.getCompanyId();
        Recruiter recruiter =recruiterService.findRecruiterById(recruiterId);
        Company company = recruiterService.findCompanyById(companyId);
//        company.addRecruiter(recruiter);
        System.out.println("Company updated after recruiter was added.");
    }

    public static Company findByName(String companyName) throws Exception {
        if (companyName == null || companyName.isBlank()) return null;
        return allCompanies.stream()
                .filter(c -> c.getName().equalsIgnoreCase(companyName))
                .findFirst()
                .orElseThrow(() -> new Exception("Company could not be found, please check the name again or create a new company."));
    }
}