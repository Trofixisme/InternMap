package com.group.InternMap.Recruiter;

import com.group.InternMap.Application.Application;
import com.group.InternMap.Application.ApplicationRepo;
import com.group.InternMap.Company.Company;
import com.group.InternMap.Company.CompanyRepo;
import com.group.InternMap.DTO.JobPostingFactory;
import com.group.InternMap.DTO.RecruiterRegistrationDTO;
import com.group.InternMap.Job.JobPosting;
import com.group.InternMap.Job.JobPostingService;
import com.group.InternMap.Job.JobRepo;
import com.group.InternMap.Student.Student;
import com.group.InternMap.User.UserRepo;
import com.group.InternMap.User.UserRole;
import com.group.InternMap.User.UserService;
import com.group.InternMap.User.Users;
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

    CompanyRepo companyRepo;
    UserRepo userRepo;
    RecruiterService recruiterService;
    UserService userService;
    JobPostingService jobPostingService;
    JobRepo jobRepo;
    ApplicationRepo applicationRepo;

    @Autowired
    public RecruiterController(UserRepo userRepo, RecruiterService recruiterService, CompanyRepo companyRepo, UserService userService, JobPostingService jobPostingService, JobRepo jobRepo, ApplicationRepo appRepo) {
        this.recruiterService = recruiterService;
        this.userService = userService;
        this.userRepo = userRepo;
        this.companyRepo = companyRepo;
        this.jobPostingService = jobPostingService;
        this.applicationRepo = appRepo;
        this.jobRepo = jobRepo;
    }

    @GetMapping("/recruiter/company/register")
    public String showRegisterCompany(Model model) {
        model.addAttribute("company", new Company());
        return "CompanyRegister";
    }

    @PostMapping("/recruiter/company/register")
    public String RegisterCompany(@ModelAttribute("company") Company company, Model model) {
        try {
            companyRepo.save(company);
            model.addAttribute("success", "Company created successfully.");
            return "redirect:/recruiter/register";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "CompanyRegister";
        }

    }
    @GetMapping("/recruiter/register")
    public String showRegisterRecruiter(Model model) {
        model.addAttribute("form", new RecruiterRegistrationDTO());
        return "RecruiterRegister";
    }

    @PostMapping("/recruiter/register")
    public String registerRecruiter(@ModelAttribute("form") RecruiterRegistrationDTO recruiterRegistrationDTO, Model model) {
        try {
            recruiterRegistrationDTO.setCompany(companyRepo.findCompanyByName(recruiterRegistrationDTO.getCompany().getName()));
            Company company = recruiterRegistrationDTO.getCompany();
            Recruiter user = recruiterRegistrationDTO.getUser();
            user.setRole(UserRole.RECRUITER);
            userService.register(user);
                if (company != null) {
                    recruiterService.addCompanyToRecruiter(user.getId(), company.getId());
                    userRepo.save(user);
                }

            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "RecruiterRegister";
        }
    }


    @PreAuthorize("hasRole('RECRUITER')")
    @GetMapping("/JobPostingForm")
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public String AddJobPostingForm(Model model, Principal principal) {
        try {
            JobPostingFactory jobPostingFactory = new JobPostingFactory();
            String jobType = "Internship";

            jobPostingFactory.getJobPosting().setRecruiter((Recruiter) userRepo.findByEmail(principal.getName()).get());
//        model.addAttribute("companyName", companyName);
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
        jobPostingFactory.setCompany(companyRepo.findCompanyByName(jobPostingFactory.getCompany().getName()));

        try {
            jobPostingFactory.getJobPosting().setCompany(companyRepo.findCompanyByName(jobPostingFactory.getCompany().getName()));
            jobPostingFactory.getJobPosting().setRecruiter((Recruiter) userRepo.findByEmail(principal.getName()).get());
            switch (jobPostingFactory.getJobType()) {
                case "FullTime" -> jobRepo.save(jobPostingFactory.toInternship());
                case "FreelanceProject" -> jobRepo.save(jobPostingFactory.toFullTime());
                case "Internship" -> jobRepo.save(jobPostingFactory.toFreelanceProject());
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

        List<JobPosting> myJobs = jobPostingService.getJobPostingsByRecruiterId((userRepo.findByEmail(principal.getName()).get()).getId());
        model.addAttribute("myJobs", myJobs);
        return "recruiter-jobPostings";
    }

    @PreAuthorize("hasRole('RECRUITER')")
    @GetMapping("/JobPostings/{jobId}/applications")
    public String viewApplications(@PathVariable long jobId, Model model, RedirectAttributes redirectAttributes) {

        try {
            JobPosting job = jobPostingService.findJobPostingByID(jobId);
            if (job == null) {
                System.out.println("Job is null.");
                redirectAttributes.addFlashAttribute("error", "Job not found");
                return "redirect:/JobPostings";
            }

            List<Application> apps = applicationRepo.findByJobPosting(job);

            System.out.println("Fetched applications count: " + (apps == null ? 0 : apps.size()));
            if (apps != null) {
                for (Application a : apps) {
                    System.out.println("App id=" + a.getApplicationID() + ", app.jobPostingId=" + (a.getJobPosting() == null ? "null" : a.getJobPosting().getId()));
                }
            }

            model.addAttribute("jobPosting", job);
            model.addAttribute("applications", apps);
            return "ViewApplicationDetail";

        } catch (Exception e) {
            System.out.println("Error");
            redirectAttributes.addFlashAttribute("error", "Error loading applications");
            return "redirect:/JobPostings";
        }
    }

    @PreAuthorize("hasRole('RECRUITER') or hasRole('ADMIN')")
    @GetMapping("/cv/{email}")
    public String viewCV(@PathVariable String email, Model model) {
        try {
            Optional<Users> retrievedStudent = userRepo.findByEmail(email);
            if (retrievedStudent.isEmpty()) {
                model.addAttribute("error", "Student could not be found");
                return "ViewApplicationDetail";
            }
            Student user = (Student) retrievedStudent.get();
            model.addAttribute("user", user);
            model.addAttribute("type", "student");
            model.addAttribute("student", user);
            return "profile";
        } catch (Exception e) {
            model.addAttribute("error", "Error loading application");
            return "ViewApplicationDetail";
        }
    }

}