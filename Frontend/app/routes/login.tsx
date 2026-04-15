import type {Route} from "./+types/home";
import Login from "~/FrontendWebpages/Login";
import {useSubmit} from "react-router";

export function meta({}: Route.MetaArgs) {
    return [
        { title: "Log in" },
        { name: "description", content: "Welcome to our 4th semester project's Log in page" },
    ];
}

export async function action({request}: Route.ActionArgs) {
    const form = request.formData();
    const response = await fetch("http://localhost:8050/login", {
        method: "POST",
        body: JSON.stringify({form}),
        headers: {
            "Content-Type": "application/json; charset=UTF-8"
        }
    })

    if (response.ok) {
        return Response.redirect(new URL("/", request.url));ty
    }
}

export function HydrateFallback() {
    return <div>Loading...</div>;
}

export default function login({loaderData}: Route.ComponentProps) {
    return <Login/>;
}
