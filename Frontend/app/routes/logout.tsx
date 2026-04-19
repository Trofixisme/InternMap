import type { Route } from "./+types/login";
import Loading from "~/FrontendWebpages/fragments/Loading";

export async function clientLoader({request}: { request: Request}) {

    localStorage.removeItem("token");

    history.go(-1)
}

export function HydrateFallback() {
    return <Loading/>;
}

export default function login({loaderData}: Route.ComponentProps) {
    return <Loading />;
}