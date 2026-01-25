package com.group.InternMap.Services;

import com.group.InternMap.Model.Job.JobPosting;
import com.group.InternMap.Model.User.Application;
import com.group.InternMap.Model.User.Company.Company;
import com.group.InternMap.Model.User.Company.Recruiter;
import com.group.InternMap.Repo.BaseRepository;
import com.group.InternMap.Repo.RepositoryAccessors;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;
import static com.group.InternMap.Repo.RepositoryAccessors.allApplications;

@Service
public class RecruiterService extends UserService {

//    private final BaseRepository<JobPosting> jobRepo = new BaseRepository<>(JobPosting.class, jobPostingPath);
//    private final BaseRepository<Application> applicationRepo = new BaseRepository<>(Application.class, applicationPath);
//    private final BaseRepository<Company> companyRepo = new BaseRepository<>(Company.class, companyPath);
    private final ApplicationEventPublisher eventPublisher;

    public RecruiterService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }
    public Recruiter findRecruiterById(UUID recruiterId) {
        if (recruiterId == null ) {
            throw new IllegalArgumentException("recruiterId must be provided");
        }

        return RepositoryAccessors.allUsers.stream()
                .filter(u -> u instanceof Recruiter)
                .map(u -> (Recruiter) u)
                .filter(r -> r.getUserID().equals(recruiterId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Recruiter not found: " + recruiterId));
    }

    public Company findCompanyById(String companyId) {
        if (companyId == null || companyId.isBlank()) {
            throw new IllegalArgumentException("companyId must be provided");
        }

        // Debug: print all companies
        RepositoryAccessors.allCompanies.forEach(c -> {
//            System.out.println("Company ID: " + c.getCompanyID() + ", Name: " + c.getName());
        });

        for (Company c : RepositoryAccessors.allCompanies) {
            System.out.println("Checking Company: " + c);
        }

        System.out.println("======================");
        System.out.println("Company ID: " + companyId);


        return RepositoryAccessors.allCompanies.stream()
//                .filter(c -> c.getCompanyID().toString().equals(companyId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Company not found: " + companyId));
    }

    public void addCompanyToRecruiter(UUID recruiterId, String companyId) throws Exception {

        Recruiter recruiter = findRecruiterById(recruiterId);
        Company company = CompanyService.findByName(companyId);

        recruiter.addCompany(company);
//        eventPublisher.publishEvent(new RecruiterAddedEvent(recruiter.getUserID(), company.getCompanyID().toString()));
    }

//    public List<Company> viewAllCompanies() throws Exception {
//        return companyRepo.findAll();
//    }
//
//    public List<Application> viewAllApplications() throws Exception {
//        return applicationRepo.findAll();
//    }
//
//    public Application searchApplication(UUID appId) throws Exception {
//        List<Application> applications = allApplications.findAll();
//        return applications.stream().filter(app -> app.getApplicationID().equals(appId)).findFirst().orElse(null);
//    }
//
//    public List<Application> filterApplication(UUID appId) throws Exception {
//        List<Application> applications = applicationRepo.findAll();
//        return applications.stream().filter(app -> app.getApplicationID().equals(appId)).collect(Collectors.toList());
//    }

    //function to display all applications for a specific jobPosting
    public List<Application> getApplicationsByJobPosting(JobPosting jobPosting) {
        if (jobPosting == null) {
            throw new IllegalArgumentException("Job posting cannot be null");
        }
        List<Application> applications = jobPosting.getApplication();
        applications.sort((a1, a2) -> a1.getApplicationDate().compareTo(a2.getApplicationDate()));
        return applications;
    }

    public static Application findByEmail(String email) throws Exception{
        if (email == null || email.isBlank()) return null;
        return   allApplications.stream()
                .filter(c -> c.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElseThrow(() -> new Exception("Profile could not be found, please check the name again or create a new company."));
    }

    private final ArrayList<Application> appRepo = RepositoryAccessors.allApplications;
    public List<Application> searchApplication(String searchQuery) throws Exception {
        return appRepo.stream().filter(app -> {
            boolean matches = false;

            if (searchQuery != null && !searchQuery.isBlank()) {
                matches |= app.getEmail().toLowerCase(Locale.ROOT).contains(searchQuery.toLowerCase(Locale.ROOT));
                matches |= app.getFName().toLowerCase(Locale.ROOT).contains(searchQuery.toLowerCase(Locale.ROOT));
                matches |= app.getLName().toLowerCase(Locale.ROOT).contains(searchQuery.toLowerCase(Locale.ROOT));
            }
            return matches;
        }).toList();
    }
}