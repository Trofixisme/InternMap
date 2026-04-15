class JobPosting {

    id: bigint;
    jobDescription: String;
    jobRequirements: String;
    jobName: String;

    company: Company;

    jobLocation: String;
    payout: number;
    duration: number;
    postingDate: Date;
    recruiterEmail: String;

    constructor(id: bigint, jobDescription: String, jobRequirements: String, jobName: String, company: Company, jobLocation: String, payout: number, duration: number, postingDate: Date, recruiterEmail: String) {
        this.id = id;
        this.jobDescription = jobDescription;
        this.jobRequirements = jobRequirements;
        this.jobName = jobName;
        this.company = company;
        this.jobLocation = jobLocation;
        this.payout = payout;
        this.duration = duration;
        this.postingDate = postingDate;
        this.recruiterEmail = recruiterEmail;
    }
}