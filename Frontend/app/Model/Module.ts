class Module {

    name: String;
    id: bigint;
    description: String;
    allSkills: Skill[];

    constructor(name: String, id: bigint, description: String, allSkills: Skill[]) {
        this.name = name;
        this.id = id;
        this.description = description;
        this.allSkills = allSkills;
    }
}