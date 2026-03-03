package com.group.InternMap.Company;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepo extends JpaRepository<Company, Long> {
    Company findCompanyById(long id);
    Company findCompanyByName(String companyName);
}
