package com.group.InternMap.Recruiter;

import com.group.InternMap.Company.Company;
import com.group.InternMap.DTO.RecruiterRegistrationDTO;
import com.group.InternMap.Deprecated.Repository.RepositoryAccessors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import static com.group.InternMap.Deprecated.Repository.RepositoryAccessors.allCompanies;

@Controller
public class RecruiterController {
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
    @GetMapping("/recruiter/register")
    public String showRegisterRecruiter(Model model, RecruiterRegistrationDTO recruiterRegistrationDTO) {
        model.addAttribute("form", new RecruiterRegistrationDTO());
        System.out.println(RepositoryAccessors.ALL_USERS);
        System.out.println(RepositoryAccessors.allCompanies);
        return "RecruiterRegister";
    }
    //    @PostMapping("/recruiter/register")
//    public String registerRecruiter(@ModelAttribute("form") RecruiterRegistrationDTO recruiterRegistrationDTO, Model model) {
//
//        try {
//            Company company = recruiterRegistrationDTO.getCompany();
//            Recruiter user = recruiterRegistrationDTO.getUser();
//
//            System.out.println(company);
//            System.out.println(company);
//            if (UserService.isEmailValid(user.getEmail())) {
//                userService.register(user);
//                if (company != null) {
//                    recruiterService.addCompanyToRecruiter(user.getId(), company.getName());
//                    System.out.println(allCompanies);
//                    System.out.println(ALL_USERS);
//                }
//            }
//            // exception here
//            return "redirect:/login";
//        } catch(Exception e) {
//            model.addAttribute("errorMessage", e.getMessage());
//            return "RecruiterRegister";
//        }
//    }
}
