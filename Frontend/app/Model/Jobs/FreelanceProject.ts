class FreelanceProject extends JobPosting {

    duration: number;
    payout: number;
    jobLocation: String;

    constructor(id: bigint, jobDescription: String, jobRequirements: String, jobName: String, company: Company, jobLocation: String, postingDate: Date, recruiter: Recruiter, duration: number, payout: number) {
        super(id, jobDescription, jobRequirements, jobName, company, jobLocation, postingDate, recruiter);
        this.duration = duration;
        this.payout = payout;
        this.jobLocation = jobLocation;
    }
}