import type { Route } from "./+types/login";
import Login from "~/FrontendWebpages/Login";
import Loading from "~/FrontendWebpages/fragments/Loading";

export function loader() {
    return {};
}

export function HydrateFallback() {
    return <Loading/>;
}

export default function login({loaderData}: Route.ComponentProps) {
    return <Login/>;
}