package com.group.InternMap.Recruiter;

import com.group.InternMap.Company.Company;
import com.group.InternMap.Company.CompanyRepo;
import com.group.InternMap.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class RecruiterService extends UserService {

    RecruiterRepo recruiterRepo;
    CompanyRepo  companyRepo;

    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public RecruiterService(RecruiterRepo recruiterRepo, CompanyRepo companyRepo, ApplicationEventPublisher eventPublisher) {
        this.recruiterRepo = recruiterRepo;
        this.eventPublisher = eventPublisher;
        this.companyRepo = companyRepo;
    }

    public Recruiter findRecruiterById(long recruiterId) {
                return recruiterRepo.findRecruiterById(recruiterId);
    }

    public void addCompanyToRecruiter(long recruiterId, long companyId) {
        try {
            Recruiter recruiter = findRecruiterById(recruiterId);
            Company company = companyRepo.findCompanyById(companyId);
            recruiter.addCompany(company);
            eventPublisher.publishEvent(new RecruiterAddedEvent(recruiter.getId(), company.getId()));
        }
        catch(Exception e){
            System.out.println(e);
        }

    }
}