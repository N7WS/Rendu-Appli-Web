import LoginForm from "~/components/custom/forms/LoginForm";

import { Separator } from "~/components/ui/separator";

import { ActionFunctionArgs, createCookie } from "@remix-run/node";
import { redirect, useActionData } from "@remix-run/react";
import { API_PATH } from "~/root";

export const jwtCookie = createCookie("jwtCookie");

export async function action({
    request
}: ActionFunctionArgs) {
    const body = await request.formData();

    return fetch(`http://${API_PATH}/users/login`, {
        method: "POST",
        credentials: "include", // Permet d'accepter les cookies HTTPOnly du backend
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            email: body.get("email"),
            password: body.get("password"),
        }),
    }).then((response) => {
        if (response.ok) {
            // Le cookie JWT est automatiquement stocké par le navigateur
            return redirect("/board/", {
                headers: {
                    "Set-Cookie": response.headers.get("Set-Cookie") || "",
                    "Content-Type": "application/json",
                }
            });
        } else {
            console.log("zadaz")
            throw new Response(response.statusText, {
                status: response.status,
                statusText: response.statusText,
            });
        }
    }).catch((error) => {
        console.error("Error during login:", error);
        throw new Response(error.message, {
            status: error.status,
            statusText: error.message,
        });
    })
}

export default function Index() {
    const data = useActionData<typeof action>();
    return (
        <>
            <LoginForm />
            <Separator className="w-[30rem] my-4"/>
            <p className="text-gray-600">Vous n'avez pas de compte ? <a href="/register" className="text-blue-600 hover:underline">Inscrivez vous dès maintenant !</a></p>
        </>
    )
};