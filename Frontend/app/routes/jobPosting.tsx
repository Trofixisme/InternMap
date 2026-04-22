import type { Route } from "./+types/home";
import JobPosting from "~/FrontendWebpages/JobPosting";
import Loading from "~/FrontendWebpages/fragments/Loading";

export function meta({}: Route.MetaArgs) {
    return [
        { title: "InternMap" },
        { name: "description", content: "Welcome to our 4th semester project" },
    ];
}

export async function clientLoader() {
    const jobPostingsRes = await fetch("http://localhost:8050/api/jobposting/jobform");
    const data = await jobPostingsRes.json();

    console.log("API response:", data);

    return {
        jobPostings: data,  // ✅ use the already-parsed data, not .json() again cool
    };
}

export function HydrateFallback() {
    return <Loading />;
}

export default function Home({ loaderData }: Route.ComponentProps) {
    const {  jobPostings } = loaderData;

    return <JobPosting  jobPostings={jobPostings} />;
}