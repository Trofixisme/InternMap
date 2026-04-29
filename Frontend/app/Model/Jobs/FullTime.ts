class FullTime extends JobPosting {

    benefits: String;

    constructor(id: bigint, jobDescription: String, jobRequirements: String, jobName: String, company: Company, jobLocation: String, postingDate: Date, recruiter: Recruiter, benefits: String) {
        super(id, jobDescription, jobRequirements, jobName, company, jobLocation, postingDate, recruiter);
        this.benefits = benefits;
    }
}