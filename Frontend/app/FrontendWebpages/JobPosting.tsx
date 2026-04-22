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
function JobPostingForm(){
    async function handleSubmit(e) {
        e.preventDefault();

        const formData = new FormData(e.currentTarget);

        const body = {
            jobname: formData.get("title"),
            jobRequirements:  formData.get(""),
            name: formData.get(""),
            description: formData.get(""),
            skills: formData.get(""),

        };

        console.log(body);

}