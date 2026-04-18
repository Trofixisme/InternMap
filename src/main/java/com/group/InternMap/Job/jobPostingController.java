package com.group.InternMap.Job;

import com.group.InternMap.Application.Application;
import com.group.InternMap.Application.ApplicationRepo;
import com.group.InternMap.Company.CompanyService;
import com.group.InternMap.DTO.JobPostingFactory;
import com.group.InternMap.Recruiter.Recruiter;
import com.group.InternMap.Recruiter.RecruiterController;
import com.group.InternMap.User.UserRole;
import com.group.InternMap.User.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/jobposting")
public class jobPostingController {
    CompanyService companyService;
    UserService userService;
    JobPostingService jobPostingService;
    @Autowired
    jobPostingController(
            CompanyService companyService,
    UserService userService,
    JobPostingService jobPostingService){
        this.companyService=companyService;
        this.userService=userService;
        this.jobPostingService=jobPostingService;
    }

    @PostMapping("/new")
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public void AddJobPosting(@ModelAttribute JobPostingFactory jobPostingFactory, Principal principal, Authentication authentication) {

        if (authentication != null && authentication.getAuthorities().toString().equals("[ROLE_" + UserRole.RECRUITER + "]")) {
            jobPostingFactory.setCompany(companyService.findByName(jobPostingFactory.getCompany().getName()));

            jobPostingFactory.getJobPosting().setCompany(companyService.findByName(jobPostingFactory.getCompany().getName()));
            jobPostingFactory.getJobPosting().setRecruiter((Recruiter) userService.searchByEmail(principal.getName()).get());
            switch (jobPostingFactory.getJobType()) {
                case "FullTime" -> jobPostingService.save(jobPostingFactory.toInternship());
                case "FreelanceProject" -> jobPostingService.save(jobPostingFactory.toFullTime());
                case "Internship" -> jobPostingService.save(jobPostingFactory.toFreelanceProject());
            }
        } else {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "User must be of role RECRUITER to proceed");
        }
    }




    @GetMapping("/jobpostings")
    public List<JobPosting> getAllJobPostings() {

        ArrayList<JobPosting> jobPostings = (ArrayList<JobPosting>) jobPostingService.getAllJobPostings();
        System.out.println(jobPostings);

        return jobPostings;
    }
    @PostMapping("/jobpostings/search")
    public List<JobPosting> searchJobPosting(@RequestParam("searchQuery") String searchQuery, Model model) {
        List<JobPosting> results = jobPostingService.searchJobs(searchQuery);

        model.addAttribute("jobPostings", results);
        return results;
    }

}
