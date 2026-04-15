class Skill {

    name: String;
    id: bigint;
    description: String;
    resourceLinks: String[];

    constructor(name: String, id: bigint, description: String, resourceLinks: String[]) {
        this.name = name;
        this.id = id;
        this.description = description;
        this.resourceLinks = resourceLinks;
    }
}