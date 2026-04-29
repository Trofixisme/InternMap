class JobPosting {

    id: bigint;
    jobDescription: String;
    jobRequirements: String;
    jobName: String;

    company: Company;

    jobLocation: String;
    datePosted: Date;
    recruiter: Recruiter;

    constructor(id: bigint, jobDescription: String, jobRequirements: String, jobName: String, company: Company,
                jobLocation: String, postingDate: Date, recruiter: Recruiter) {
        this.id = id;
        this.jobDescription = jobDescription;
        this.jobRequirements = jobRequirements;
        this.jobName = jobName;
        this.company = company;
        this.jobLocation = jobLocation;
        this.datePosted = postingDate;
        this.recruiter = recruiter;
    }
}