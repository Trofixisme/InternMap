package com.group.InternMap.Recruiter;
import com.group.InternMap.User.UserService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
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

   // public void addCompanyToRecruiter(long recruiterId, String companyId) throws Exception {
//  it was added by event publisher , causing issues

}