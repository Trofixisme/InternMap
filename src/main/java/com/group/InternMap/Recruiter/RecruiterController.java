package com.group.InternMap.Recruiter;

import com.group.InternMap.Company.Company;
import com.group.InternMap.Company.CompanyRepo;
import com.group.InternMap.DTO.RecruiterRegistrationDTO;
import com.group.InternMap.Deprecated.Repository.RepositoryAccessors;
import com.group.InternMap.User.UserRepo;
import com.group.InternMap.User.UserRole;
import com.group.InternMap.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import static com.group.InternMap.Deprecated.Repository.RepositoryAccessors.ALL_USERS;
import static com.group.InternMap.Deprecated.Repository.RepositoryAccessors.allCompanies;

@Controller
public class RecruiterController {

    CompanyRepo companyRepo;
    UserRepo userRepo;
    RecruiterService recruiterService;

    @Autowired
    public RecruiterController(UserRepo userRepo, RecruiterService recruiterService, CompanyRepo companyRepo) {
        this.recruiterService = recruiterService;
        this.userRepo = userRepo;
        this.companyRepo = companyRepo;
    }

    @GetMapping("/company/register")
    public String showRegisterCompany(Model model) {
        model.addAttribute("company", new Company());
        return "CompanyRegister";
    }

    @PostMapping("/company/register")
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
    public String showRegisterRecruiter(Model model, RecruiterRegistrationDTO recruiterRegistrationDTO) {
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

            if (UserService.isEmailValid(user.getEmail())) {
                userRepo.save(user);

                if (company != null) {
                    recruiterService.addCompanyToRecruiter(user.getId(), company.getId());
                    userRepo.save(user);
                }
            }
            // exception here
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "RecruiterRegister";
        }
    }
}
