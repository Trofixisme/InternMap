package com.group.InternMap.Deprecated;

import com.group.InternMap.Company.Company;

import java.util.ArrayList;
import java.util.List;

@Deprecated
@SuppressWarnings("all")
public class CompanyRepository extends BaseRepository {

    public CompanyRepository() {
        super("data/companies.txt");
    }

    //TODO: FIX
    @Override
            //String companyUUID , String industry, String name, String websiteURL, ArrayList<String> location, Recruiter... recruiters
    Company parseObject(String line) {
        String[] parts = line.split("\\|", -1);
        String companyUUID=parts[0];
        String industry=parts[1];
        String name=parts[2];
        String websiteURL=parts[3];

        ArrayList<String> locations = new ArrayList<>(List.of(parts[4].split(",")));

//        Recruiter recruiter = parseObject(parts[5])   ; // if constructor expects only ID

//        return new Company(
//                companyUUID,        // companyUUID
//                industry,        // industry
//                name,        // name
//                websiteURL,        // website URL
//                locations,       // list of locations
//                parseObject(Recruiter)        // varargs, passed as 1 recruiter
//        );
        return null;
    }

}