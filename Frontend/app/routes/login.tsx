import type {Route} from "./+types/home";
import Login from "~/FrontendWebpages/Login";
import {redirect, useSubmit} from "react-router";
import Loading from "~/FrontendWebpages/fragments/Loading";

export function meta({}: Route.MetaArgs) {
    return [
        { title: "Log in" },
        { name: "description", content: "Welcome to our 4th semester project's Log in page" },
    ];
}

export async function clientAction({ request }: Route.ClientActionArgs) {
    const formData = await request.formData();

    const loginData = {
        email: formData.get("email") as string,
        password: formData.get("password") as string,
    };


    const response = await fetch("http://localhost:8050/api/auth/login", {
        method: "POST",
        body: JSON.stringify(loginData),
        headers: { "Content-Type": "application/json" },
        credentials: "include",
    });

    if (response.ok) {
        throw redirect("/"); // cookie already stored by browser
    } else {
        const errorText = await response.text();
        return {
            error: errorText || "Invalid email or password",
        };
    }
}
export function HydrateFallback() {
    return <Loading/>;
}

export default function login({loaderData}: Route.ComponentProps) {
    return <Login/>;
}
