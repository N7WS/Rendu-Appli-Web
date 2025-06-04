import type { LinksFunction } from "@remix-run/node";
import {
  isRouteErrorResponse,
  Links,
  Meta,
  Outlet,
  Scripts,
  ScrollRestoration,
  useRouteError,
} from "@remix-run/react";
import N7WSlogo from "~/public/N7WS.png";
import "./tailwind.css";

export const links: LinksFunction = () => [
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

export const API_PATH = "carpe.enseeiht.fr:8080"; //process.env.API_PATH || "localhost:8080";

export default function App() {
  return (
    <html lang="en">
      <head>
        <meta charSet="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <Meta />
        <Links />
      </head>
      <body>
        <Outlet />
        <ScrollRestoration />
        <Scripts />
      </body>
    </html>
  );
}

export function ErrorBoundary() {
  const error = useRouteError();

  console.log(error);

  let title = "Unexpected Error";
  let message = "Something went wrong.";

  if (isRouteErrorResponse(error)) {
    title = `${error.status} - ${error.statusText}`;
    message = error.data || message;
  } else if (error instanceof Error) {
    message = error.message;
  }

  return (
    <html lang="en">
      <head>
        <title>{title}</title>
        <Meta />
        <Links />
      </head>
      <body className="relative min-h-screen bg-gray-100">
        {/* Logo en haut à gauche */}
        <img alt="logo" src={N7WSlogo} className="absolute top-4 left-4 w-28" />

        {/* Contenu centré verticalement et horizontalement */}
        <div className="flex flex-col items-center justify-center min-h-screen text-center px-8">
          <h1 className="text-4xl font-bold mb-4 text-blue-400">{title}</h1>
          <p className="text-lg text-gray-700">{message}</p>
        </div>

        <ScrollRestoration />
        <Scripts />
      </body>
    </html>
  );
}
