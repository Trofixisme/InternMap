package com.group.InternMap.Recruiter;

import com.group.InternMap.Company.Company;
import com.group.InternMap.Company.CompanyRepo;
import com.group.InternMap.DTO.RecruiterRegistrationDTO;
import com.group.InternMap.User.UserRole;
import com.group.InternMap.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class RecruiterService extends UserService {

    RecruiterRepo recruiterRepo;
    CompanyRepo companyRepo;
    UserService userService;

    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public RecruiterService(RecruiterRepo recruiterRepo, CompanyRepo companyRepo, UserService userService, ApplicationEventPublisher eventPublisher) {
        this.recruiterRepo = recruiterRepo;
        this.eventPublisher = eventPublisher;
        this.companyRepo = companyRepo;
        this.userService = userService;
    }

    public void registerRecruiter(RecruiterRegistrationDTO recruiterRegistrationDTO) throws Exception {
        recruiterRegistrationDTO.setCompany(companyRepo.findCompanyByName(recruiterRegistrationDTO.getCompany().getName()));
        Company company = recruiterRegistrationDTO.getCompany();
        Recruiter user = recruiterRegistrationDTO.getUser();
        user.setRole(UserRole.RECRUITER);
        userService.register(user);
        if (company != null) {
            addCompanyToRecruiter(user.getId(), company.getId());
            recruiterRepo.save(user);
        }
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