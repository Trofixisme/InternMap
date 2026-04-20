package com.group.InternMap.Recruiter;

import com.group.InternMap.Application.Application;
import com.group.InternMap.Application.ApplicationRepo;
import com.group.InternMap.Company.Company;
import com.group.InternMap.Company.CompanyService;
import com.group.InternMap.DTO.JobPostingFactory;
import com.group.InternMap.DTO.RecruiterRegistrationDTO;
import com.group.InternMap.Job.JobPosting;
import com.group.InternMap.Job.JobPostingService;
import com.group.InternMap.Student.Student;
import com.group.InternMap.User.UserService;
import com.group.InternMap.User.Users;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class RecruiterController {

    CompanyService companyService;
    RecruiterService recruiterService;
    UserService userService;
    JobPostingService jobPostingService;
    ApplicationRepo applicationRepo;

    Logger logger = LoggerFactory.getLogger(RecruiterController.class);

    @Autowired
    public RecruiterController(RecruiterService recruiterService, CompanyService companyService, UserService userService, JobPostingService jobPostingService, ApplicationRepo appRepo) {
        this.recruiterService = recruiterService;
        this.userService = userService;
        this.companyService = companyService;
        this.jobPostingService = jobPostingService;
        this.applicationRepo = appRepo;
    }

    @GetMapping("/recruiter/company/register")
    public String showRegisterCompany(Model model) {
        model.addAttribute("company", new Company());
        return "CompanyRegister";
    }

    @PostMapping("/recruiter/company/register")
    public String RegisterCompany(@ModelAttribute("company") Company company, Model model) {

        companyService.save(company);
        model.addAttribute("success", "Company created successfully.");
        return "redirect:/register";

    }

    @PostMapping("/recruiter/register")
    public String registerRecruiter(HttpServletRequest request, @ModelAttribute("form") RecruiterRegistrationDTO recruiterRegistrationDTO, Model model) {
        try {
            recruiterService.registerRecruiter(recruiterRegistrationDTO, request);

        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "register";
        }

        return "redirect:/";
    }

    @PreAuthorize("hasRole('RECRUITER')")
    @GetMapping("/JobPostingForm")
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public String AddJobPostingForm(Model model, Principal principal) {
        try {
            JobPostingFactory jobPostingFactory = new JobPostingFactory();
            String jobType = "Internship";

            jobPostingFactory.getJobPosting().setRecruiter((Recruiter) userService.searchByEmail(principal.getName()).get());

//          model.addAttribute("companyName", companyName);
            model.addAttribute("jobTypeSelect", jobType);
            model.addAttribute("jobPostingFactory", jobPostingFactory);

            return "JobPostingForm";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @PostMapping("/JobPostingForm")
    @PreAuthorize("hasRole('RECRUITER')")
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public String AddJobPosting(@ModelAttribute JobPostingFactory jobPostingFactory, Principal principal, Model model) {
//        String companyName = (String) model.getAttribute("companyName");
        jobPostingFactory.setCompany(companyService.findByName(jobPostingFactory.getCompany().getName()));

        try {
            jobPostingFactory.getJobPosting().setCompany(companyService.findByName(jobPostingFactory.getCompany().getName()));
            jobPostingFactory.getJobPosting().setRecruiter((Recruiter) userService.searchByEmail(principal.getName()).get());
            switch (jobPostingFactory.getJobType()) {
                case "FullTime" -> jobPostingService.save(jobPostingFactory.toInternship());
                case "FreelanceProject" -> jobPostingService.save(jobPostingFactory.toFullTime());
                case "Internship" -> jobPostingService.save(jobPostingFactory.toFreelanceProject());
            }

            model.addAttribute("success", "Job posting created successfully!");
            return "redirect:/JobPostings"; // or return to form with a success message

        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            model.addAttribute("jobPostingFactory", jobPostingFactory);
            return "JobPostingForm";
        }
    }

    @PreAuthorize("hasRole('RECRUITER')")
    @GetMapping("/recruiter/jobpostings")
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public String getRecruiterJobPostings(Model model, Principal principal) {

        List<JobPosting> myJobs = jobPostingService.getJobPostingsByRecruiterId((userService.searchByEmail(principal.getName()).get()).getId());
        model.addAttribute("myJobs", myJobs);
        return "recruiter-jobPostings";
    }

    @PreAuthorize("hasRole('RECRUITER')")
    @GetMapping("/JobPostings/{jobId}/applications")
    public String viewApplications(@PathVariable long jobId, Model model, RedirectAttributes redirectAttributes) {

        JobPosting job = jobPostingService.findJobPostingByID(jobId);
        if (job == null) {
            logger.warn("Job with ID {} not found", jobId);
            redirectAttributes.addFlashAttribute("error", "Job not found");
            return "redirect:/JobPostings";
        }

        List<Application> apps = applicationRepo.findByJobPosting(job);

        logger.info("Fetched applications count: {}", (apps == null ? 0 : apps.size()));
        if (apps != null) {
            for (Application a : apps) {
                logger.info("Application ID: {}, Job Posting ID: {}", a.getApplicationID(), (a.getJobPosting() == null ? "null" : a.getJobPosting().getId()));
            }
        }

        model.addAttribute("jobPosting", job);
        model.addAttribute("applications", apps);
        return "ViewApplicationDetail";

    }

    @PreAuthorize("hasRole('RECRUITER') or hasRole('ADMIN')")
    @GetMapping("/cv/{email}")
    public String viewCV(@PathVariable String email, Model model) {

        Optional<Users> retrievedStudent = userService.searchByEmail(email);
        if (retrievedStudent.isEmpty()) {
            model.addAttribute("error", "Student could not be found");
            return "ViewApplicationDetail";
        }
        Student user = (Student) retrievedStudent.get();
        model.addAttribute("user", user);
        model.addAttribute("type", "student");
        model.addAttribute("student", user);
        return "profile";

    }

}