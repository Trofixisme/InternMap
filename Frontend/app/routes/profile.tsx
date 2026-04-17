import type { Route } from "./+types/login";
import Login from "~/FrontendWebpages/Login";
import Loading from "~/FrontendWebpages/fragments/Loading";
import Profile from "~/FrontendWebpages/Profile";

export async function clientLoader() {

    const data = await fetch("http://localhost:8050/REST/profile", {
        credentials: "include", // ensures cookie is sent
        headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
    });

    return await data.json()
}

export function HydrateFallback() {
    return <Loading/>;
}

export default function profile({loaderData}: Route.ComponentProps) {
    return <Profile userDetails={loaderData}  />;
}