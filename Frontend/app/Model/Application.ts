class Application {

    id: bigint
    fName: string
    lName: string
    email: string
    phoneNumber: string
    applicationDate: Date
    jobPosting: JobPosting

    constructor(id: bigint, fName: string, lName: string, email: string, phoneNumber: string, applicationDate: Date, jobPosting: JobPosting) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.applicationDate = applicationDate;
        this.jobPosting = jobPosting;
    }
}