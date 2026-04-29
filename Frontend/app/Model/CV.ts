class CV {

    cvId: bigint;
    description: String;
    pastExperiences: String;
    projects: String;

    constructor(cvId: bigint, description: String, pastExperiences: String, projects: String) {
        this.cvId = cvId;
        this.description = description;
        this.pastExperiences = pastExperiences;
        this.projects = projects;
    }
}