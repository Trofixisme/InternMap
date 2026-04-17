class User {

    email: String;
    fname: String;
    lname: String;
    role: String;
    id: bigint;

    constructor(email: String, fname: String, lname: String, role: String, id: bigint) {
        this.email = email;
        this.fname = fname;
        this.lname = lname;
        this.role = role;
        this.id = id;
    }
}