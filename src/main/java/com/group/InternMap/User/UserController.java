package com.group.InternMap.User;

import com.group.InternMap.Admin.Admin;
import com.group.InternMap.DTO.RecruiterRegistrationDTO;
import com.group.InternMap.Company.Company;
import com.group.InternMap.Recruiter.Recruiter;
import com.group.InternMap.Deprecated.Repository.RepositoryAccessors;
import com.group.InternMap.Company.CompanyService;
import com.group.InternMap.Recruiter.RecruiterService;
import com.group.InternMap.Student.Student;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import static com.group.InternMap.Deprecated.Repository.RepositoryAccessors.ALL_USERS;
import static com.group.InternMap.Deprecated.Repository.RepositoryAccessors.allCompanies;

@Controller
public class UserController {
    private final UserService userService;
    private final RecruiterService recruiterService;
    private final CompanyService companyService;

    public UserController(UserService userService, RecruiterService recruiterService, CompanyService companyService) {
        this.userService = userService;
        this.recruiterService = recruiterService;
        this.companyService = companyService;
    }

    @GetMapping("/student/register")
    public String showRegisterStudent(Model model) {
        model.addAttribute("user", new Student());
        return "StudentRegister";
    }

    @PostMapping("/student/register")
    public String registerStudent(@ModelAttribute("user") Student user, Model model) {
        try {
            var email = user.getEmail();
            if (UserService.isEmailValid(email)) {
                userService.register(user);
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            // Return the view name (DO NOT REDIRECT)
            return "StudentRegister";
        }
        // Only redirect on SUCCESS
        return "redirect:/login";
    }

    @GetMapping("/recruiter/register")
    public String showRegisterRecruiter(Model model, RecruiterRegistrationDTO recruiterRegistrationDTO) {
        model.addAttribute("form", new RecruiterRegistrationDTO());
        System.out.println(RepositoryAccessors.ALL_USERS);
        System.out.println(RepositoryAccessors.allCompanies);
        return "RecruiterRegister";
    }

    @PostMapping("/recruiter/register")
    public String registerRecruiter(@ModelAttribute("form") RecruiterRegistrationDTO recruiterRegistrationDTO, Model model) {

        try {
            Company company = recruiterRegistrationDTO.getCompany();
            Recruiter user = recruiterRegistrationDTO.getUser();

            System.out.println(company);
            System.out.println(company);
            if (UserService.isEmailValid(user.getEmail())) {
                userService.register(user);
                if (company != null) {
                    recruiterService.addCompanyToRecruiter(user.getId(), company.getName());
                    System.out.println(allCompanies);
                    System.out.println(ALL_USERS);
                }
            }
            // exception here
            return "redirect:/login";
        } catch(Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "RecruiterRegister";
        }
    }

    @GetMapping("/company/register")
    public String showRegisterCompany(Model model) {
        model.addAttribute("company", new Company());
        return "CompanyRegister";
    }

    @PostMapping("/company/register")
    public String RegisterCompany(@ModelAttribute("company") Company company, Model model) {
        try {
            allCompanies.add(company);
            model.addAttribute("success", "Company created successfully.");
            return"redirect:/login";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "CompanyRegister";
        }

    }

    @GetMapping("/admin/register")
    public String showRegisterAdmin(Model model) {
        model.addAttribute("user", new Admin());
        return "adminRegister";
    }

    @PostMapping("/admin/register")
    public String registerAdmin(@ModelAttribute("user") Admin user, Model model) {
        try {
            if (UserService.isEmailValid(user.getEmail())) {
                userService.register(user);
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            // Return the view name (DO NOT REDIRECT)
            return "adminRegister";
        }
        // Only redirect on SUCCESS
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showloginPage(Model model) {
        model.addAttribute("user", new Users());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") Users users, Model model, HttpSession session) {
        try {
            Users authenticatedUsers = userService.login(users.getEmail(), users.getPlainPassword());
            session.setAttribute("loggedInUser", authenticatedUsers);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "login";
        }
        System.out.println("User's logged in");
        return "redirect:/";
    }
}