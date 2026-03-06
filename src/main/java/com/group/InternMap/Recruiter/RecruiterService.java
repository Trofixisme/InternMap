package com.group.InternMap.Recruiter;
import com.group.InternMap.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import java.util.Locale;


@Service
public class RecruiterService extends UserService {

    RecruiterRepo  recruiterRepo;
    private final ApplicationEventPublisher eventPublisher;

    public RecruiterService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Autowired
    public RecruiterService(RecruiterRepo recruiterRepo, ApplicationEventPublisher eventPublisher) {
        this.recruiterRepo = recruiterRepo;
        this.eventPublisher = eventPublisher;
    }

    public Recruiter findRecruiterById(long recruiterId) {
                return recruiterRepo.findRecruiterById(recruiterId);
    }

//    public void addCompanyToRecruiter(long recruiterId, String companyId) throws Exception {
   // It was added by the event publisher and was causing issues

}