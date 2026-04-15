import type {Route} from "./+types/home";
import Welcome from "~/FrontendWebpages/Welcome";
import Loading from "~/FrontendWebpages/fragments/Loading";

export function meta({}: Route.MetaArgs) {
  return [
    { title: "InternMap" },
    { name: "description", content: "Welcome to our 4th semester project" },
  ];
}

export async function clientLoader({params}: Route.ClientLoaderArgs) {
  const roadmaps = await fetch(`http://localhost:8050/REST/`);
  const jobPostings = await fetch(`http://localhost:8050/REST/jobpostings`);
  return [await roadmaps.json(), await jobPostings.json()];
}

export function HydrateFallback() {
  return <Loading/>;
}

export default function home({loaderData}: Route.ComponentProps) {
  return <Welcome roadmaps={loaderData[0]} jobPostings={loaderData[1]} />;
}
