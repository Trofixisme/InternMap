import ApplicationForm  from "~/FrontendWebpages/ApplicationForm";
import {useLoaderData} from "react-router";
//  step one delete whatever the first line was
// step two create the webpage and get the clientLoader to fetch the data from the controller and pass it to the webpage
// and then create the dashboard function that will return the webpage with the data from the clientLoader and then create the clientAction function that will handle the delete action and then create the meta function that will set the title and description of the page

export function meta() {
    return [
        { title: "InternMap" },
        { name: "description", content: "Welcome to our 4th semester project" },
    ];
}

export async function clientLoader({ request }) {
    const url = new URL(request.url);
    const jobId = url.searchParams.get("jobId");

    const res = await fetch(`http://localhost:8050/api/application/apply?jobId=${jobId}`, {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
    });

    if (!res.ok) {
        throw new Response("Failed to load application page", { status: res.status });
    }

    return await res.json();
}
// @ts-ignore

export default function ApplyRoute() {
    const data = useLoaderData();

    return <ApplicationForm jobPostings={data} />;
}