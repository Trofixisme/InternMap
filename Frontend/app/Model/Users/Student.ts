class Student extends User {

    graduatingYear: number;
    studentMajor: String;
    faculty: String;
    uniName: String;
    cv: CV;
    applications: Application[];

    constructor(email: String, fname: String, lname: String, role: String, id: bigint, dateCreated: Date, graduatingYear: number, studentMajor: String, faculty: String, uniName: String, cv: CV, applications: Application[]) {
        super(email, fname, lname, role, id, dateCreated);
        this.graduatingYear = graduatingYear;
        this.studentMajor = studentMajor;
        this.faculty = faculty;
        this.uniName = uniName;
        this.cv = cv;
        this.applications = applications;
    }
}