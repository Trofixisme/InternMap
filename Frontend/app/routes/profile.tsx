import type { Route } from "./+types/login";
import Loading from "~/FrontendWebpages/fragments/Loading";
import Profile from "~/FrontendWebpages/Profile";

export function meta({}: Route.MetaArgs) {
    return [
        { title: "Profile" },
        { name: "Your own profile", content: "Welcome to our 4th semester project" },
    ];
}

export async function clientLoader() {

    if (!localStorage.getItem("token")) {
        return Response.redirect("/login", 302);
    }

    const data = await fetch("http://localhost:8050/REST/profile", {
        credentials: "include", // ensures cookie is sent
        headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
    });

    if (!data.ok) {
        return Response.redirect("/login", 302);
    }

    return await data.json()
}

export function HydrateFallback() {
    return <Loading/>;
}

export default function profile({loaderData}: Route.ComponentProps) {
    const user: User = loaderData as User;

    return <Profile userDetails={user} />;
}