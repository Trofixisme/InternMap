    package com.group.InternMap.Application;

    import com.group.InternMap.DTO.ApplicationAndCVDTO;
    import com.group.InternMap.Job.JobPosting;
    import com.group.InternMap.Job.JobPostingService;
    import com.group.InternMap.Notification.NotificationService;
    import com.group.InternMap.Student.Student;
    import com.group.InternMap.Student.StudentRepo;
    import com.group.InternMap.User.UserRole;
    import com.group.InternMap.User.UserService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.core.Authentication;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.client.HttpClientErrorException;
    import org.springframework.web.server.ResponseStatusException;
    import org.springframework.web.servlet.mvc.support.RedirectAttributes;

    import java.security.Principal;
    import java.util.List;
    @RestController
    @RequestMapping("/api/application")
    public class applicationController {

        StudentRepo studentRepo;
        JobPostingService jobPostingService;
        ApplicationRepo applicationRepo;
        NotificationService notificationService;
        UserService userService;

        @Autowired
        applicationController(StudentRepo studentRepo, JobPostingService jobPostingService, ApplicationRepo applicationRep, NotificationService notificationService, UserService userService){
            this.applicationRepo=applicationRep;
            this.studentRepo=studentRepo;
            this.jobPostingService=jobPostingService;
            this.notificationService=notificationService;
            this.userService=userService;
        }


        // useless just to load the page and then redirect to the same page with the form
        @GetMapping("/apply")
        public List<JobPosting> writing(@RequestParam Long jobId ,  Authentication authentication)  {
            if (jobPostingService.findJobPostingByID(jobId) == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid job ID");
            }
            if (!authentication.isAuthenticated()) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User must be authenticated to apply for a job");
            }
            if (!authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_STUDENT"))) {
                System.out.println("dum user role: " + authentication.getAuthorities());
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User must be of role STUDENT to apply for a job");
            }
            return jobPostingService.getAllJobPostings();
        }

        @PostMapping("/apply/submit")
        public ResponseEntity<String> saving(
                @RequestParam Long jobId,
                @RequestParam String fname,
                @RequestParam String lname,
                @RequestParam String phone,
                @RequestParam String email,
                Authentication authentication,
                Principal principal) {

            JobPosting jobPosting = jobPostingService.findJobPostingByID(jobId);
                System.out.println(jobPosting);
                Application application = new Application();
                application.setJobPosting(jobPosting);
                System.out.println(principal.getName());
                Student student = studentRepo.findByEmail(principal.getName());
                System.out.println(student);
                application.setStudent(student);
                application.setEmail(email);
                application.setFName(fname);
                application.setLName(lname);
                application.setPhoneNumber(phone);
                System.out.println(application);
                applicationRepo.save(application);
                ApplicationAndCVDTO applicationAndCVDTO = new ApplicationAndCVDTO();
                applicationAndCVDTO.setApplication(application);
                applicationAndCVDTO.setStudent(student);
                String recruiterEmail = jobPosting.getRecruiterEmail();
                System.out.println(recruiterEmail);
                notificationService.sendToUser(recruiterEmail, authentication.getName() + " has applied to " + applicationAndCVDTO.getApplication().getJobPosting().getJobName());
                return ResponseEntity.ok("works");



        }

        @GetMapping
        @SuppressWarnings("OptionalGetWithoutIsPresent")
        public List<Application> getStudentApplications(Principal principal, Authentication authentication) {

            if (authentication != null && authentication.getAuthorities().toString().equals("[ROLE_" + UserRole.STUDENT + "]")) {
                return applicationRepo.findByStudentEmail((userService.searchByEmail(principal.getName()).get()).getEmail());
            } else {
                throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "User must be of role STUDENT to proceed");
            }
        }
    }
