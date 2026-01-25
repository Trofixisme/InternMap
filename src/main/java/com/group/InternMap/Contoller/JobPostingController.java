package com.group.InternMap.Contoller;

import com.group.InternMap.Model.Job.*;
import com.group.InternMap.Model.User.Admin;
import com.group.InternMap.Model.User.Application;
import com.group.InternMap.Model.User.Student;
import com.group.InternMap.Services.JobPostingService;
import com.group.InternMap.Services.UserService;
import org.springframework.ui.Model;
import com.group.InternMap.Model.User.Company.Recruiter;
import com.group.InternMap.Services.RecruiterService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static com.group.InternMap.Repo.RepositoryAccessors.allJobPostings;

//to do:application controller, and review HTML
@Controller
public class JobPostingController {
    private final RecruiterService recruiterService;
    private final JobPostingService jobPostingService;

    public JobPostingController(RecruiterService recruiterService, JobPostingService jobPostingService, UserService userService) {
        this.recruiterService = recruiterService;
        this.jobPostingService = jobPostingService;
    }

    //JobPostings
    @GetMapping("/JobPostings")
    public String getAllJobPostings(Model model, ArrayList<JobPosting> jobposting, HttpSession session) {
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

            jobposting = (ArrayList<JobPosting>) jobPostingService.getAllJobPostings();
            model.addAttribute("jobPostings", jobposting);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            model.addAttribute("error", "Failed to load job postings");
        }
        return "JobPosting"; // Thymeleaf template
    }

//    @GetMapping("/JobPostingForm")
//    public String AddJobPostingForm(Model model, HttpSession session) {
//        if (session.getAttribute("loggedInUser") == null || !(session.getAttribute("loggedInUser") instanceof Recruiter recruiter)) {
//            return "redirect:/login";
//        }
//        JobPosting jobPosting = new JobPosting();
//        jobPosting.setFullTime(new FullTime());
//        jobPosting.setInternship(new Internship());
//        jobPosting.setFreeLanceProject(new FreeLanceProject());
//        jobPosting.setRecruiter(recruiter);
//        model.addAttribute("jobPosting", jobPosting);
//        return "JobPostingForm";
//    }
@GetMapping("/JobPostingForm")
public String AddJobPostingForm(Model model, HttpSession session) {
    if (session.getAttribute("loggedInUser") == null ||
            !(session.getAttribute("loggedInUser") instanceof Recruiter recruiter)) {
        return "redirect:/login";
    }

    JobPosting jobPosting = new JobPosting();

    // IMPORTANT: Initialize all nested objects
//    jobPosting.setFullTime(new FullTime());
//    jobPosting.setInternship(new Internship());
//    jobPosting.setFreeLanceProject(new FreeLanceProject());

    jobPosting.setRecruiter(recruiter);
    model.addAttribute("jobPosting", jobPosting);
    return "JobPostingForm";
}

    @PostMapping("/JobPostingForm")
    public String AddJobPosting(@ModelAttribute JobPosting jobPosting,
                                HttpSession session,
                                Model model) {
        if (session.getAttribute("loggedInUser") == null ||
                !(session.getAttribute("loggedInUser") instanceof Recruiter recruiter)) {
            return "redirect:/login";
        }

        try {

//            if (jobPosting.getJobPostingType() == PostingType.FullTime) {
//                jobPosting.setInternship(null);
//                jobPosting.setFreeLanceProject(null);
//            } else if (jobPosting.getJobPostingType() == PostingType.Internship) {
//                jobPosting.setFullTime(null);
//                jobPosting.setFreeLanceProject(null);
//            } else if (jobPosting.getJobPostingType() == PostingType.FreeLanceProject) {
//                jobPosting.setFullTime(null);
//                jobPosting.setInternship(null);
//            }
            jobPosting.setRecruiter(recruiter);
            allJobPostings.add(jobPosting);

            model.addAttribute("success", "Job posting created successfully!");
            return "redirect:/JobPostings"; // or return to form with success message

        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            model.addAttribute("jobPosting", jobPosting);
            return "JobPostingForm";
        }
    }

//
//    @PostMapping("/JobPostingForm")
//    public String saveJobPosting(@ModelAttribute JobPosting jobposting, HttpSession session, Model model) {
//        if (session.getAttribute("loggedInUser") == null) {
//            return "redirect:/login";
//        }
//        Recruiter user = (Recruiter) session.getAttribute("loggedInUser");
//        try { // link posting to recruiter
//            allJobPostings.add(jobposting);
//            jobposting.setRecruiter(user);
//
//            return "redirect:/JobPostings";
//
//        } catch (Exception e) {
//            model.addAttribute("error", "Failed to add job posting");
//            return "JobPostingForm";
//        }
//    }

    @PostMapping("/JobPostings/search")
    public String searchJobPosting(@RequestParam("searchQuery") String searchQuery, @ModelAttribute JobPosting jobposting, Model model, HttpSession session) {
        try {
            List<JobPosting> results = jobPostingService.searchJobPostings(searchQuery.replaceFirst(",", ""));
            // Add search results to the model
            model.addAttribute("jobPostings", results);
            model.addAttribute("jobposting", jobposting);
        } catch (Exception e) {
            model.addAttribute("error", "Error searching job postings: " + e.getMessage());
        }
        return "JobPosting"; // Thymeleaf template
    }

    @GetMapping("/recruiter/jobpostings")
    public String getRecruiterJobPostings(Model model, HttpSession session) throws Exception {
        Recruiter recruiter = (Recruiter) session.getAttribute("loggedInUser");
        if (recruiter == null) {
            return "redirect:/login";
        }
//        List<JobPosting> myJobs = jobPostingService.getJobPostingsByRecruiterId(recruiter.getUserID());
//        model.addAttribute("myJobs", myJobs);
        return "recruiter-jobpostings";
    }

    @GetMapping("/JobPostings/{jobId}/applications")
    public String viewApplications(@PathVariable UUID jobId,
                                   Model model,
                                   HttpSession session,
                                   RedirectAttributes redirectAttributes) {
        Recruiter recruiter = (Recruiter) session.getAttribute("loggedInUser");
        if (recruiter == null) {
            return "redirect:/login";
        }

        try {
            JobPosting job = jobPostingService.findByID(jobId);
            if (job == null) {
                System.out.println("Job is null.");
                redirectAttributes.addFlashAttribute("error", "Job not found");
                return "redirect:/JobPostings";
            }
            List<Application> apps = recruiterService.getApplicationsByJobPosting(job);
            model.addAttribute("jobPosting", job);
            model.addAttribute("applications", apps);
            return "ViewApplicationDetail"; //I still don't have it but need to do it for clicking the view button

        } catch (Exception e) {
            System.out.println("Error");
            redirectAttributes.addFlashAttribute("error", "Error loading applications");
            return "redirect:/JobPostings";
        }
    }

    @GetMapping("/cv/{email}")
    public String viewCV(@PathVariable("email") String email, Model model, HttpSession session) {
        try {
            Student retrievedStudent = (Student) new UserService().searchByEmail(email);
            model.addAttribute("user", retrievedStudent);
            model.addAttribute("student", retrievedStudent);
            model.addAttribute("type", "student");
            return "profile";
        } catch (Exception e) {
            model.addAttribute("error", "Error loading application");
            return "ViewApplicationDetail";
        }
    }
}