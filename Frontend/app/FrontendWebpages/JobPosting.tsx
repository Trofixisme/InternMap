import "~/CSS/jobPosting.css"
import "~/CSS/InternMapHomepage.css";
import {IndexFooter, IndexHeader} from "./fragments/IndexHeaderAndFooter";
import {
    Button,
    Description,
    FieldError,
    FieldGroup,
    Fieldset,
    Form,
    Input,
    Label,
    Tabs,
    TextField
} from "@heroui/react";
import type {Selection} from "@heroui/react";
import { Dropdown, Header   } from "@heroui/react";
import {useState} from "react";
// @ts-ignore
export default function JobPosting({ jobPostings }) {
    const [selected, setSelected] = useState<Selection>(new Set(["apple"]));

    async function handleSubmit(e) {

        e.preventDefault();
        const formData = new FormData(e.currentTarget);
        const body = {
            jobType: Array.from(selected)[0],
            jobPosting: {
                jobName: formData.get("jobtitle"),
                jobDescription: formData.get("JobDisc"),
                jobRequirements: formData.get("Jobrec"),
            },
            company: {
                name: formData.get("CompanyN"),
            }
        };

        try {
            const res = await fetch(`http://localhost:8050/api/jobposting/jobform/Create`, {
                method: "POST",
                body: JSON.stringify(body),
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${localStorage.getItem("token")}`,

                },
            });
            console.log("testing")
            if (!res.ok) console.error( "Submission failed:", res.status);
        } catch (err) {
            console.error("Network error:", err);
        }
    }


        return (

        <>
            <IndexHeader/>

            <div className="wrapper">

                <div  align="center" >

                    <Form method="post" className="w-full max-w-96" onSubmit={handleSubmit}>
                        <Fieldset>
                            <Description>Apply to Application!</Description>
                            <FieldGroup>
                                <Dropdown style>
                                    <Button aria-label="Menu" variant="secondary">
                                        InternShip
                                    </Button>
                                    <Dropdown.Popover className="min-w-[256px]">
                                        <Dropdown.Menu
                                            selectedKeys={selected}
                                            selectionMode="single"
                                            onSelectionChange={setSelected}
                                        >
                                            <Dropdown.Section>
                                                <Header>Select a fruit</Header>
                                                <Dropdown.Item id="intern" textValue="Internship">
                                                    <Dropdown.ItemIndicator />
                                                    <Label>Internship</Label>
                                                </Dropdown.Item>
                                                <Dropdown.Item id="fulltime" textValue="fulltime">
                                                    <Dropdown.ItemIndicator />
                                                    <Label>fullTime</Label>
                                                </Dropdown.Item>
                                                <Dropdown.Item id="Freelance" textValue="Freelance">
                                                    <Dropdown.ItemIndicator />
                                                    <Label>Freelance</Label>
                                                </Dropdown.Item>
                                            </Dropdown.Section>
                                        </Dropdown.Menu>
                                    </Dropdown.Popover>
                                </Dropdown>
                                <TextField
                                    isRequired
                                    name="jobtitle"
                                    validate={(value) => {
                                        if (value.length < 3) {
                                            return "Name must be at least 3 characters";
                                        }
                                        return null;
                                    }}>


                                    <Label>Job Title</Label>
                                    <Input placeholder="Professional pro player" />
                                    <FieldError />
                                </TextField>

                                <TextField
                                    isRequired
                                    name="JobDisc"
                                    validate={(value) => {
                                        if (value.length < 3) {
                                            return "Name must be at least 3 characters";
                                        }
                                        return null;
                                    }}>


                                    <Label>Job Discription</Label>
                                    <Input placeholder="Have no life" />
                                    <FieldError />
                                </TextField>
                                <TextField
                                    isRequired
                                    name="Jobrec"
                                    validate={(value) => {
                                        if (value.length < 3) {
                                            return "Name must be at least 3 characters";
                                        }
                                        return null;
                                    }}>


                                    <Label>Job Requirement</Label>
                                    <Input placeholder="idk nothing" />
                                    <FieldError />
                                </TextField>
                                <TextField isRequired name="CompanyN" type="text">
                                    <Label>Company Name</Label>
                                    <Input placeholder="RIOOOOOOOOOOOT" />
                                    <FieldError />
                                </TextField>

                            </FieldGroup>
                            <Fieldset.Actions>
                                <Button type="submit" onSubmit={handleSubmit} >
                                    Apply

                                </Button>
                                <Button type="reset" variant="secondary">
                                    Reset
                                </Button>
                            </Fieldset.Actions>
                        </Fieldset>
                    </Form>
                </div>
            </div>

            <IndexFooter/>
        </>
    );
}
