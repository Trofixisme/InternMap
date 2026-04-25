class User {

    email: String;
    fname: String;
    lname: String;
    role: String;
    id: bigint;
    dateCreated: Date

    constructor(email: String, fname: String, lname: String, role: String, id: bigint, dateCreated: Date) {
        this.email = email;
        this.fname = fname;
        this.lname = lname;
        this.role = role;
        this.id = id;
        this.dateCreated = dateCreated;
    }
}