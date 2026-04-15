import { redirect } from "react-router";
import { apiFetch } from "./api.server";

export type UserSession = {
    username: string;
    roles: string[];
};

// Call this in any loader to get current user
// Redirects to /login if session expired
export async function requireUser(request: Request): Promise<UserSession> {
    const response = await apiFetch("/api/auth/me", request);

    if (!response.ok) {
        throw redirect("/login");
    }

    return response.json();
}

// Call this in protected loaders to require a specific role
export async function requireRole(
    request: Request,
    role: string
): Promise<UserSession> {
    const user = await requireUser(request);

    if (!user.roles.includes(role)) {
        throw redirect("/unauthorized");
    }

    return user;
}

export const isAdmin = (user: UserSession) =>
    user.roles.includes("ROLE_ADMIN");

export const isUser = (user: UserSession) =>
    user.roles.includes("ROLE_USER");