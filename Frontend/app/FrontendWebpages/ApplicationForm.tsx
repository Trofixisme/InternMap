import "~/CSS/jobPosting.css"
import "~/CSS/InternMapHomepage.css";
import {IndexFooter, IndexHeader} from "./fragments/IndexHeaderAndFooter";
import {Tabs} from "@heroui/react";
import {
    Button,
    Description,
    FieldError,
    FieldGroup,
    Fieldset,
    Form,
    Input,
    Label,
    TextArea,
    TextField,
} from "@heroui/react";

// @ts-ignore
export default function ApplicationForm({ jobPostings}) {
    const jobId = new URL(window.location.href).searchParams.get("jobId");

    async function handleSubmit(e) {
        e.preventDefault();

        const formData = new FormData(e.currentTarget);

        const params = new URLSearchParams({
            jobId: jobId ?? "",
            fname: formData.get("fname") as string,
            lname: formData.get("lname") as string,
            phone: formData.get("phone") as string,
            email: formData.get("email") as string,
        });

        const res = await fetch(
            `http://localhost:8050/api/application/apply/submit?${params}`,
            {
                method: "POST",
                headers: {
                    Authorization: `Bearer ${localStorage.getItem("token")}`,
                },
            }
        );

        if (!res.ok) {
            const errorText = await res.text();
            console.error("Failed to submit application", res.status, errorText);
            return;
        }

        console.log("Application submitted!");
    }

    return (

        <>
            <IndexHeader/>
<div className="wrapper">
            <div  align="center" >

                <Form method="post" className="w-full max-w-96" onSubmit={handleSubmit}>
                    <input
                        type="hidden"
                        name="jobId"
                        value={new URL(window.location.href).searchParams.get("jobId") ?? ""}
                    />
                    <Fieldset>
                        <Description>Apply to Application!</Description>
                        <FieldGroup>
                            <TextField
                                isRequired
                                name="fname"
                                validate={(value) => {
                                    if (value.length < 3) {
                                        return "Name must be at least 3 characters";
                                    }
                                    return null;
                                }}>


                                <Label>fname</Label>
                                <Input placeholder="John Doe" />
                                <FieldError />
                            </TextField>
                            <TextField
                                isRequired
                                name="lname"
                                validate={(value) => {
                                    if (value.length < 3) {
                                        return "Name must be at least 3 characters";
                                    }
                                    return null;
                                }}>


                                <Label>lname</Label>
                                <Input placeholder="John Doe" />
                                <FieldError />
                            </TextField>
                            <TextField
                                isRequired
                                name="phone"
                                validate={(value) => {
                                    if (value.length < 3) {
                                        return "Name must be at least 3 characters";
                                    }
                                    return null;
                                }}>


                                <Label>phone</Label>
                                <Input placeholder="+201xxxxxxxxx" />
                                <FieldError />
                            </TextField>
                            <TextField isRequired name="email" type="email">
                                <Label>Email</Label>
                                <Input placeholder="john@example.com" />
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