class Recruiter extends User {

    title: string
    companies: Company[]

    constructor(email: String, fname: String, lname: String, role: String, id: bigint, dateCreated: Date, title: string, companies: Company[]) {
        super(email, fname, lname, role, id, dateCreated);
        this.title = title;
        this.companies = companies;
    }
}