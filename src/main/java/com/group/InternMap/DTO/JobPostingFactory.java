package com.group.InternMap.DTO;

import com.group.InternMap.Company.Company;
import com.group.InternMap.Job.FreelanceProject;
import com.group.InternMap.Job.FullTime;
import com.group.InternMap.Job.Internship;
import com.group.InternMap.Job.JobPosting;

public class JobPostingFactory {

    private final JobPosting jobPosting = new JobPosting();
    private final FullTime fullTime = new FullTime();
    private final Internship internship = new Internship();
    private final FreelanceProject freelanceProject = new FreelanceProject();
    private Company company ;
    private String jobType;
//    = new Company()

    public Internship toInternship() {
        internship.setRecruiter(jobPosting.getRecruiter());
        internship.setJobName(jobPosting.getJobName());
        internship.setJobDescription(jobPosting.getJobDescription());
        internship.setJobRequirements(jobPosting.getJobRequirements());
        return internship;
    }

    public FreelanceProject toFreelanceProject() {
        freelanceProject.setRecruiter(jobPosting.getRecruiter());
        freelanceProject.setJobName(jobPosting.getJobName());
        freelanceProject.setJobDescription(jobPosting.getJobDescription());
        freelanceProject.setJobRequirements(jobPosting.getJobRequirements());
        return freelanceProject;
    }

    public FullTime toFullTime() {
        fullTime.setRecruiter(jobPosting.getRecruiter());
        fullTime.setJobName(jobPosting.getJobName());
        fullTime.setJobDescription(jobPosting.getJobDescription());
        fullTime.setJobRequirements(jobPosting.getJobRequirements());
        return fullTime;
    }

    public JobPosting getJobPosting() {
        return jobPosting;
    }

    public FullTime getFullTime() {
        return fullTime;
    }

    public Internship getInternship() {
        return internship;
    }

    public FreelanceProject getFreelanceProject() {
        return freelanceProject;
    }
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }
}