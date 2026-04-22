import "~/CSS/jobPosting.css"
import "~/CSS/InternMapHomepage.css";
import { useState } from "react";
import { IndexFooter, IndexHeader } from "./fragments/IndexHeaderAndFooter";
import {
    Button,
    Description,
    FieldError,
    FieldGroup,
    Fieldset,
    Form,
    Input,
    Label,
    TextField,
} from "@heroui/react";

// @ts-ignore
export default function RoadMapCreate({ }) {
    const jobId = new URL(window.location.href).searchParams.get("jobId");

    const [modules, setModules] = useState([
        { skills: [{}] }
    ]);

    function addModule() {
        setModules([...modules, { skills: [{}] }]);
    }

    function removeModule(moduleIndex) {
        setModules(modules.filter((_, i) => i !== moduleIndex));
    }

    function addSkill(moduleIndex) {
        const updated = modules.map((mod, i) => {
            if (i !== moduleIndex) return mod;
            return { ...mod, skills: [...mod.skills, {}] };
        });
        setModules(updated);
    }

    function removeSkill(moduleIndex, skillIndex) {
        const updated = modules.map((mod, i) => {
            if (i !== moduleIndex) return mod;
            return { ...mod, skills: mod.skills.filter((_, si) => si !== skillIndex) };
        });
        setModules(updated);
    }

        async function handleSubmit(e) {
            e.preventDefault();

            const formData = new FormData(e.currentTarget);

            const body = {
                name: formData.get("title"),
                modules: modules.map((mod, moduleIndex) => ({
                    name: formData.get(`modules[${moduleIndex}].name`),
                    description: formData.get(`modules[${moduleIndex}].description`),
                    skills: mod.skills.map((_, skillIndex) => ({
                        name: formData.get(`modules[${moduleIndex}].skills[${skillIndex}].name`),
                        description: formData.get(`modules[${moduleIndex}].skills[${skillIndex}].description`),
                        links: [formData.get(`modules[${moduleIndex}].skills[${skillIndex}].links[0]`)]
                    }))
                }))
            };

            console.log(body);

            const res = await fetch(`http://localhost:8050/api/admin/roadmap/create/submit`, {
                method: "POST",
                body: JSON.stringify(body),
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${localStorage.getItem("token")}`,
                },
            });

            if (!res.ok) {
                const errorText = await res.text();
                console.error("Failed to create roadmap", res.status, errorText);
                return;
            }

            console.log("Roadmap created!");
        }

    return (
        <>
            <IndexHeader />
            <div >
                <div align="center">
                    <Form method="post" className onSubmit={handleSubmit}>
                        <Fieldset>
                            <Description style={{ fontSize: "30px" }}>Create a RoadMap</Description>
                            <FieldGroup>
                                <TextField
                                    isRequired
                                    name="title"
                                    validate={(value) => {
                                        if (value.length < 3) return "Name must be at least 3 characters";
                                        return null;
                                    }}>
                                    <Label style={{ fontSize: "20px", color: "white" }}>ROADMAP TITLE</Label>
                                    <Input placeholder="Title" />
                                    <FieldError />
                                </TextField>
                                <br />

                                {modules.map((mod, moduleIndex) => (
                                    <div key={moduleIndex}>
                                        <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center" }}>
                                            <h1 style={{ paddingLeft: "20px" }}>Module {moduleIndex + 1}</h1>
                                            {moduleIndex > 0 && (
                                                <Button type="button" onPress={() => removeModule(moduleIndex)} variant="destructive">
                                                    Remove
                                                </Button>
                                            )}
                                        </div>

                                        <div>
                                            <TextField
                                                isRequired
                                                name={`modules[${moduleIndex}].name`}
                                                validate={(value) => {
                                                    if (value.length < 3) return "Name must be at least 3 characters";
                                                    return null;
                                                }}>
                                                <Label style={{ color: "white" }}>Name</Label>
                                                <Input placeholder="Module name" />
                                                <FieldError />
                                            </TextField>
                                        </div>
                                        <br />

                                        <TextField
                                            isRequired
                                            name={`modules[${moduleIndex}].description`}
                                            validate={(value) => {
                                                if (value.length < 3) return "Name must be at least 3 characters";
                                                return null;
                                            }}>
                                            <Label style={{ color: "white" }}>Description</Label>
                                            <Input placeholder="Description" />
                                            <FieldError />
                                        </TextField>
                                        <br></br>

                                        {mod.skills.map((_, skillIndex) => (
                                            <div className="skill-card" key={skillIndex}>
                                                <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center" }}>
                                                    <span>Skill {skillIndex + 1}</span>
                                                    {skillIndex > 0 && (
                                                        <Button type="button" onPress={() => removeSkill(moduleIndex, skillIndex)} variant="destructive">
                                                            Remove
                                                        </Button>
                                                    )}
                                                </div>

                                                <TextField
                                                    isRequired
                                                    name={`modules[${moduleIndex}].skills[${skillIndex}].name`}
                                                    validate={(value) => {
                                                        if (value.length < 3) return "Name must be at least 3 characters";
                                                        return null;
                                                    }}>
                                                    <Label>Skill Name</Label>
                                                    <Input placeholder="Skill name" />
                                                    <FieldError />
                                                </TextField>

                                                <TextField isRequired name={`modules[${moduleIndex}].skills[${skillIndex}].description`} type="text">
                                                    <Label>Skill Description</Label>
                                                    <Input placeholder="Description of the skill" />
                                                    <FieldError />
                                                </TextField>

                                                <TextField isRequired name={`modules[${moduleIndex}].skills[${skillIndex}].links[0]`} type="text">
                                                    <Label>Resource Link</Label>
                                                    <Input placeholder="https://..." />
                                                    <FieldError />
                                                </TextField>
                                            </div>

                                        ))}

                                        <br></br>
                                        <Button type="button" onPress={() => addSkill(moduleIndex)} variant="secondary">
                                            + Add Skill
                                        </Button>
                                    </div>
                                ))}

                                <Button type="button" onPress={addModule} variant="secondary">
                                    + Another Module
                                </Button>

                            </FieldGroup>

                            <div>
                                <Button type="submit">
                                    Apply
                                </Button>
                                <Button type="reset" variant="secondary">
                                    Reset
                                </Button>
                            </div>
                        </Fieldset>
                    </Form>
                </div>
            </div>

            <br /><br /><br />
            <IndexFooter />
        </>
    );
}