class Roadmap {

    id: bigint;
    name: String;
    description: String;
    allModules: Module[];

    constructor(id: bigint, name: String, description: String, allModules: Module[]) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.allModules = allModules;
    }
}