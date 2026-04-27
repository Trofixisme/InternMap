import Dashboard from "~/FrontendWebpages/Dashboard";
import type { Route } from "../+types/root";
import {useLoaderData} from "react-router";
// step one delete whatever the first line was
// step two create the webpage and get the clientLoader to fetch the data from the controller and pass it to the webpage
// and then create the dashboard function that will return the webpage with the data from the clientLoader and then create the clientAction function that will handle the delete action and then create the meta function that will set the title and description of the page

export function meta() {
    return [
        { title: "InternMap" },
        { name: "description", content: "Welcome to our 4th semester project" },
    ];
}

export async function clientLoader() {

    const idk = await fetch("http://localhost:8050/api/admin/dashboard", {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
    });

    if(!idk.ok) {
        throw new Response("Failed to fetch dashboard data", { status: idk.status });
    }

    return await idk.json()
}

// @ts-ignore
export async function clientAction({ request }) {
    const formData = await request.formData();
    const emails = formData.getAll("emails");
    const roadmaps = formData.getAll("roadmaps");

    if (emails.length > 0) {
        for (const email of emails) {
            const response = await fetch(`http://localhost:8050/api/admin/dashboard/delete?email=${email}`, {
                method: "POST",
                headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
            });
            if (!response.ok) throw new Response(`Failed to delete user: ${email}`, { status: response.status });
        }
    }

    if (roadmaps.length > 0) {
        for (const id of roadmaps) {
            const response = await fetch(`http://localhost:8050/api/admin/dashboard/delete/roadmap?id=${id}`, {
                method: "POST",
                headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
            });
            if (!response.ok) throw new Response(`Failed to delete roadmap: ${id}`, { status: response.status });
        }
    }

    return { success: true };
}

export default function dashboard({ }: Route.ComponentProps) {
    const loaderData = useLoaderData();

    return (
        <Dashboard
            users={loaderData.users}
            roadmaps={loaderData.roadmaps}
        />
    );}
