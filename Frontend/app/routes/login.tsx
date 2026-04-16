import type { Route } from "./+types/login";
import Login from "~/FrontendWebpages/Login";
import Loading from "~/FrontendWebpages/fragments/Loading";

export function loader() {
    return {};
}

async function handleLogin(e: React.FormEvent<HTMLFormElement>) {
    e.preventDefault();

    const formData = new FormData(e.currentTarget);

    const loginData = {
        email: formData.get("email"),
        password: formData.get("password"),
    };

    const response = await fetch("http://localhost:8050/api/auth/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(loginData),
    });

    const data = await response.json();

    localStorage.setItem("token", data.token);

    window.location.href = "/";
}
export function HydrateFallback() {
    return <Loading/>;
}

export default function login({loaderData}: Route.ComponentProps) {
    return <Login/>;
}