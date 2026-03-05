package com.group.InternMap.Recruiter;
import com.group.InternMap.Application.ApplicationRepo;
import com.group.InternMap.Job.JobPosting;
import com.group.InternMap.Job.JobRepo;
import com.group.InternMap.User.UserService;
import com.group.InternMap.Application.Application;
import com.group.InternMap.Deprecated.Repository.RepositoryAccessors;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


@Service
public class RecruiterService extends UserService {
    RecruiterRepo  recruiterRepo;
    //    private final BaseRepository<JobPosting> jobRepo = new BaseRepository<>(JobPosting.class, jobPostingPath);
//    private final BaseRepository<Application> applicationRepo = new BaseRepository<>(Application.class, applicationPath);
//    private final BaseRepository<Company> companyRepo = new BaseRepository<>(Company.class, companyPath);
    private final ApplicationEventPublisher eventPublisher;

    public RecruiterService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public Recruiter findRecruiterById(long recruiterId) {
                return recruiterRepo.findRecruiterById(recruiterId);
    }

//    public Company findCompanyById(String companyId) {
//        if (companyId == null || companyId.isBlank()) {
//            throw new IllegalArgumentException("companyId must be provided");
//        }
//
//    }

        // Debug: print all companies



   // public void addCompanyToRecruiter(long recruiterId, String companyId) throws Exception {
//  it was added by event publisher , causing issues

//    public List<Company> viewAllCompanies() throws Exception {
//        return companyRepo.findAll();
//   }
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
    JobRepo jobRepo;

    //function to display all applications for a specific jobPosting
    public List<Application> getApplicationsByJobPostingId(Long jobPostingId) {
        if (jobPostingId == null) {
            throw new IllegalArgumentException("Job posting cannot be null");
        }
        JobPosting joposting = jobRepo.findJobPostingById(jobPostingId);
        List<Application> applications = joposting.getApplication();

        return applications;
    }
    ApplicationRepo applicationRepo;


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