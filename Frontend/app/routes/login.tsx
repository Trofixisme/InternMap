import type { Route } from "./+types/login";
import Login from "~/FrontendWebpages/Login";
import { redirect } from "react-router";
import Loading from "~/FrontendWebpages/fragments/Loading";

export function loader() {
    return {};
}

export async function action({ request }: Route.ActionArgs) {
    const formData = await request.formData();

    // Convert FormData to JSON object (because your backend uses @RequestBody)
    const loginData = {
        email: formData.get("email") as string,
        password: formData.get("password") as string,
    };

    const response = await fetch("http://localhost:8050/api/auth/login", {
        method: "POST",
        body: JSON.stringify(loginData),           // ← Must be JSON string
        headers: {
            "Content-Type": "application/json",    // ← Correct header
        },
    });

    if (response.ok) {
        // Better way to redirect in Remix/React Router v7
        return redirect("/");
    } else {
        // Important: Handle login failure
        const errorText = await response.text();
        throw new Response(errorText || "Invalid email or password", {
            status: response.status,
            statusText: "Login Failed",
        });
    }
}

export function HydrateFallback() {
    return <Loading/>;
}

export default function login({loaderData}: Route.ComponentProps) {
    return <Login/>;
}