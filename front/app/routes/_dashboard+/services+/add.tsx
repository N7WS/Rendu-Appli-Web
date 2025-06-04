import { ActionFunctionArgs, LoaderFunctionArgs } from "@remix-run/node"
import { redirect, useLoaderData } from "@remix-run/react";
import ServiceForm from "~/components/custom/forms/ServiceForm"

import { getAllScripts, getAllServices } from "~/lib/apiRequest";
import { Script } from "~/lib/utils";

export async function loader({
    request
}: LoaderFunctionArgs) {
    const token = request.headers.get("Cookie")?.split('; ').find(row => row.startsWith('jwt='))?.split('=')[1] || '';

    const servicesData = await getAllServices(token);
    const scriptsData = await getAllScripts(token);
    return { 
        services: servicesData,
        scripts: scriptsData,
        token: token
    };
}

export async function action({
    request
}: ActionFunctionArgs) {
    const body = await request.formData();
    const scripts = JSON.parse(body.getAll("scriptsId").join(""));

    const token = request.headers.get("Cookie")?.split('; ').find(row => row.startsWith('jwt='))?.split('=')[1] || '';
    return fetch("http://localhost:8080/services/add", {
        method: "POST",
        credentials: "include", // Permet d'accepter les cookies HTTPOnly du backend
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`, // Ajoute le token JWT dans l'en-tête Authorization
        },
        body: JSON.stringify({
            name: body.get("name"),
            port: body.get("port"),
            scriptsId: scripts
        }),
    }).then((response) => {
        if (response.ok) {
            return redirect("/services")
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


export default function AddService() {
    const { scripts } = useLoaderData<{ scripts: Script[]}>();
    return (
        <div className="w-full mt-8 flex justify-center">
            <ServiceForm scripts={scripts} />
        </div>
    )
}