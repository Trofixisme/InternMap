package com.group.InternMap.Job;

import com.group.InternMap.Admin.Admin;
import com.group.InternMap.Application.Application;
import com.group.InternMap.Application.ApplicationRepo;
import com.group.InternMap.Company.CompanyRepo;
import com.group.InternMap.DTO.JobPostingFactory;
import com.group.InternMap.Recruiter.Recruiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Controller
public class JobPostingController {

    ApplicationRepo applicationRepo;
    JobPostingService jobPostingService;
    JobRepo jobRepo;
    CompanyRepo companyRepo;
    InternshipRepo internshipRepo;
    FullTimeRepo fullTimeRepo;
    FreelanceProjectRepo freelanceProjectRepo;

    @Autowired
    public JobPostingController(JobPostingService jobPostingService, ApplicationRepo applicationRepo, JobRepo jobRepo, CompanyRepo companyRepo, InternshipRepo internshipRepo, FullTimeRepo fullTimeRepo, FreelanceProjectRepo freelanceProjectRepo) {
        this.jobPostingService = jobPostingService;
        this.applicationRepo = applicationRepo;
        this.jobRepo = jobRepo;
        this.companyRepo = companyRepo;
        this.internshipRepo = internshipRepo;
        this.fullTimeRepo = fullTimeRepo;
        this.freelanceProjectRepo = freelanceProjectRepo;
    }

    //JobPostings
    @GetMapping("/JobPostings")
    public String getAllJobPostings(Model model, HttpSession session) {
        try {
            // Fetch all job postings from the service
            if (session.getAttribute("loggedInUser") instanceof Admin) {
                model.addAttribute("isLoggedIn", true);
                model.addAttribute("isAdmin", true);
                model.addAttribute("isRecruiter", false);
            } else if (session.getAttribute("loggedInUser") == null) {
                model.addAttribute("isLoggedIn", false);
                model.addAttribute("isAdmin", false);
                model.addAttribute("isRecruiter", false);
            } else if (session.getAttribute("loggedInUser") instanceof Recruiter) {
                model.addAttribute("isLoggedIn", true);
                model.addAttribute("isAdmin", false);
                model.addAttribute("isRecruiter", true);
            } else {
                model.addAttribute("isLoggedIn", true);
                model.addAttribute("isAdmin", false);
                model.addAttribute("isRecruiter", false);
            }

            ArrayList<JobPosting> jobPosting = (ArrayList<JobPosting>) jobPostingService.getAllJobPostings();
            model.addAttribute("jobPostings", jobPosting);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            model.addAttribute("error", "Failed to load job postings");
        }
        return "JobPosting"; // Thymeleaf template
    }

    @PostMapping("/JobPostings/search")
    public String searchJobPosting(@RequestParam("searchQuery") String searchQuery, Model model) {
        try {
            // Search dynamically using your service
             List<JobPosting> results = jobPostingService.findJobPostingByName(searchQuery);
            // Add search results to the model
             model.addAttribute("jobPostings", results);

        } catch (Exception e) {
            model.addAttribute("error", "Error searching application: " + e.getMessage());
        }

        return "JobPosting";
    }

    @GetMapping("/JobPostingForm")
    public String AddJobPostingForm(Model model, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null || !(session.getAttribute("loggedInUser") instanceof Recruiter recruiter)) {
            return "redirect:/login";
        }

        JobPostingFactory jobPostingFactory = new JobPostingFactory();
        String companyName = "Sample";
        String jobType = "Internship";

        jobPostingFactory.getJobPosting().setRecruiter(recruiter);
//        model.addAttribute("companyName", companyName);
        model.addAttribute("jobTypeSelect", jobType);
        model.addAttribute("jobPostingFactory", jobPostingFactory);
        return "JobPostingForm";
    }

    @PostMapping("/JobPostingForm")
    public String AddJobPosting(@ModelAttribute JobPostingFactory jobPostingFactory, HttpSession session, Model model) {
        if (session.getAttribute("loggedInUser") == null || !(session.getAttribute("loggedInUser") instanceof Recruiter recruiter)) {
            return "redirect:/login";
        }

        String jobTypeSelect = (String) model.getAttribute("jobTypeSelect");
//        String companyName = (String) model.getAttribute("companyName");
        jobPostingFactory.setCompany(companyRepo.findCompanyByName(jobPostingFactory.getCompany().getName()));

        try {
            jobPostingFactory.getJobPosting().setCompany(companyRepo.findCompanyByName(jobPostingFactory.getCompany().getName()));
            jobPostingFactory.getJobPosting().setRecruiter(recruiter);
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

    @GetMapping("/recruiter/jobpostings")
    public String getRecruiterJobPostings(Model model, HttpSession session) {
        Recruiter recruiter = (Recruiter) session.getAttribute("loggedInUser");
        if (recruiter == null) {
            return "redirect:/login";
        }
        List<JobPosting> myJobs = jobPostingService.getJobPostingsByRecruiterId(recruiter.getId());
        model.addAttribute("myJobs", myJobs);
        return "recruiter-jobPostings";
    }

    @GetMapping("/JobPostings/{jobId}/applications")
    public String viewApplications(@PathVariable long jobId, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        Recruiter recruiter = (Recruiter) session.getAttribute("loggedInUser");
        if (recruiter == null) {
            return "redirect:/login";
        }

        try {
            JobPosting job = jobPostingService.findJobPostingByID(jobId);
            if (job == null) {
                System.out.println("Job is null.");
                redirectAttributes.addFlashAttribute("error", "Job not found");
                return "redirect:/JobPostings";
            }

            List<Application> apps = job.getApplications();
//            List<Application> apps =jobRepo.findByJobPosting(job);
            model.addAttribute("jobPosting", job);
            model.addAttribute("applications", apps);
            return "ViewApplicationDetail"; //I still don't have it, but need to do it for clicking the view button

        } catch (Exception e) {
            System.out.println("Error");
            redirectAttributes.addFlashAttribute("error", "Error loading applications");
            return "redirect:/JobPostings";
        }
    }

    @GetMapping("/cv/{email}")
    public String viewCV(@PathVariable("email") String email, Model model) {
        try {
//            Student retrievedStudent = (Student) new UserService().searchByEmail(email);
//            model.addAttribute("user", retrievedStudent);
//            model.addAttribute("student", retrievedStudent);
            model.addAttribute("type", "student");
            return "profile";
        } catch (Exception e) {
            model.addAttribute("error", "Error loading application");
            return "ViewApplicationDetail";
        }
    }
}