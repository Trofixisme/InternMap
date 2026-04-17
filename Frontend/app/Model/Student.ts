class Student extends User {

    graduatingYear: number;
    studentMajor: String;
    faculty: String;
    uniName: String;
    cv: CV;

    constructor(graduatingYear: number, studentMajor: String, faculty: String, uniName: String, cv: CV, email: String, fname: String, lname: String, role: String, id: bigint) {
        super(email, fname, lname, role, id);
        this.graduatingYear = graduatingYear;
        this.studentMajor = studentMajor;
        this.faculty = faculty;
        this.uniName = uniName;
        this.cv = cv;
    }
}