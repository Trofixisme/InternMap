package com.group.InternMap.Company;

import com.group.InternMap.User.UserRole;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/company")
public class companyController {
    CompanyService companyService;
    companyController(){

    }

    @PostMapping("/new")
    public void RegisterCompany(@RequestBody Company company, Authentication authentication) {
        if (authentication != null && authentication.getAuthorities().toString().equals("[ROLE_" + UserRole.ADMIN + "]")) {
            companyService.save(company);
        }
    }
}
