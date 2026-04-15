import {
  isRouteErrorResponse,
  Links,
  Meta,
  Outlet,
  Scripts,
  ScrollRestoration,
} from "react-router";

import type { Route } from "./+types/root";
import "./app.css";

export const links: Route.LinksFunction = () => [
  { rel: "preconnect", href: "https://fonts.googleapis.com" },
  {
    rel: "preconnect",
    href: "https://fonts.gstatic.com",
    crossOrigin: "anonymous",
  },
  {
    rel: "stylesheet",
    href: "https://fonts.googleapis.com/css2?family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&display=swap",
  },
];

export function Layout({ children }: { children: React.ReactNode }) {
  return (
    <html lang="en">
      <head>
        <meta charSet="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <Meta />
        <Links />
      </head>
      <body>
        {children}
        <ScrollRestoration />
        <Scripts />
      </body>
    </html>
  );
}

export default function App() {
  return <Outlet />;
}

export function ErrorBoundary({ error }: Route.ErrorBoundaryProps) {
  let message = "Oops!";
  let details = "An unexpected error occurred.";
  let stack: string | undefined;

  if (isRouteErrorResponse(error)) {
    message = error.status === 404 ? "Not Found" : "Error";
    details =
      error.status === 404
        ? "The requested page could not be found."
        : error.statusText || details;
  } else if (import.meta.env.DEV && error && error instanceof Error) {
    message = error.name;
    details = error.message;
    stack = error.stack;
  }

  return (
      <div className="centered">
        <a href={"/"} style={{borderRadius: "200px"}} inert><img src="/images/navi/Navi%20Unique.png" alt="Logo"
                                                                 style={{width: "100px", height: "100px"}}/></a>
        <br/>

      <main className="pt-16 p-4 container mx-auto">
      <br/>
      <h1 className="font-bold text-2xl">{message}</h1>
      <p className="text-sm text-gray-400 font-semibold">{details}</p>
      <br/>
      {stack ? <div>
        <pre className="w-full p-4 overflow-x-auto">
        <code>{stack}</code>
          </pre>
      </div> : <p>That's all we know</p>
      }
      <br/>
      </main>
    </div>
  );
}
