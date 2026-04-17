import type { Route } from "./+types/home";
import Welcome from "~/FrontendWebpages/Welcome";
import Loading from "~/FrontendWebpages/fragments/Loading";

export function meta({}: Route.MetaArgs) {
  return [
    { title: "InternMap" },
    { name: "description", content: "Welcome to our 4th semester project" },
  ];
}

export async function clientLoader() {

  const [roadmapsRes, jobPostingsRes] = await Promise.all([
    fetch("http://localhost:8050/REST/"),
    fetch("http://localhost:8050/REST/jobpostings"),
  ]);

  return {
    roadmaps: await roadmapsRes.json(),
    jobPostings: await jobPostingsRes.json(),
  };
}

export function HydrateFallback() {
  return <Loading />;
}

export default function Home({ loaderData }: Route.ComponentProps) {
  const { roadmaps, jobPostings } = loaderData;

  return <Welcome roadmaps={roadmaps} jobPostings={jobPostings} />;
}