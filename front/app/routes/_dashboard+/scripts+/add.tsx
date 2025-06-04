import { ActionFunctionArgs, redirect } from "@remix-run/node";
import ScriptForm from "~/components/custom/forms/ScriptForm";

export async function action({
    request
}: ActionFunctionArgs) {
    const body = await request.formData();
    const token = request.headers.get("Cookie")?.split('; ').find(row => row.startsWith('jwt='))?.split('=')[1] || '';
    return fetch("http://localhost:8080/scripts/add", {
        method: "POST",
        credentials: "include", // Permet d'accepter les cookies HTTPOnly du backend
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`, // Ajoute le token JWT dans l'en-tête Authorization
        },
        body: JSON.stringify({
            name: body.get("name"),
            path: body.get("path")
        }),
    }).then((response) => {
        if (response.ok) {
            return redirect("/scripts/")
        } else {
            throw new Response(response.statusText, {
                status: response.status,
                statusText: response.statusText,
            });
        }
    }).catch((error) => {
        throw new Response(error.message, {
            status: error.status,
            statusText: error.message,
        });
    })
}

export default function AddScript() {
    return (
        <div className="w-full mt-8 flex justify-center">
            <ScriptForm />
        </div>
    )
}