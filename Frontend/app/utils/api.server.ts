// Shared fetch that forwards the browser cookie to Spring Boot
export async function apiFetch(
    path: string,
    request: Request,
    options: RequestInit = {}
) {
    const cookieHeader = request.headers.get("cookie");

    const response = await fetch(`http://localhost:8050${path}`, {
        ...options,
        headers: {
            "Content-Type": "application/json",
            cookie: cookieHeader || "",
            ...options.headers,
        },
    });

    return response;
}