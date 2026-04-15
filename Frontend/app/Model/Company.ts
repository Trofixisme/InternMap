class Company {

    id: bigint;
    industry: String;
    name: String;
    websiteURL: URL;
    locationOfHQ: String;

    constructor(id: bigint, industry: String, name: String, websiteURL: URL, locationOfHQ: String) {
        this.id = id;
        this.industry = industry;
        this.name = name;
        this.websiteURL = websiteURL;
        this.locationOfHQ = locationOfHQ;
    }
}