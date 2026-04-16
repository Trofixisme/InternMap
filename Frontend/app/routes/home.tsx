import type { Route } from "./+types/home";
import Welcome from "~/FrontendWebpages/Welcome";
import Loading from "~/FrontendWebpages/fragments/Loading";
import { redirect } from "react-router";


export function meta({}: Route.MetaArgs) {
  return [
    { title: "InternMap" },
    { name: "description", content: "Welcome to our 4th semester project" },
  ];
}

export async function clientLoader({ params }: Route.ClientLoaderArgs) {

  // ✅ Check session first — cookie sent automatically by browser
  const authResponse = await fetch("http://localhost:8050/api/auth/me", {
    credentials: "include", // ensures cookie is sent
  });

  // Not logged in → redirect to login
  if (!authResponse.ok) {
    throw redirect("/login");
  }

  const user = await authResponse.json(); // { username, roles }

  // ✅ Fetch your existing data
  const [roadmapsRes, jobPostingsRes] = await Promise.all([
    fetch("http://localhost:8050/REST/", { credentials: "include" }),
    fetch("http://localhost:8050/REST/jobpostings", { credentials: "include" }),
  ]);

  return {
    user,
    roadmaps: await roadmapsRes.json(),
    jobPostings: await jobPostingsRes.json(),
  };
}

export function HydrateFallback() {
  return <Loading />;
}

export default function Home({ loaderData }: Route.ComponentProps) {
  const { user, roadmaps, jobPostings } = loaderData;

  return <Welcome user={user} roadmaps={roadmaps} jobPostings={jobPostings} />;
}