import { ActionFunctionArgs, LoaderFunctionArgs, redirect } from "@remix-run/node";
import { useLoaderData } from "@remix-run/react";
import DeviceServiceAdd from "~/components/custom/forms/DeviceServiceAdd";
import { getAllServices } from "~/lib/apiRequest";
import { Service } from "~/lib/utils";
import { API_PATH } from "~/root";


export async function loader({
    request
}: LoaderFunctionArgs) {
    const url = new URL(request.url);
    const deviceName = url.searchParams.get("name");
    const token = request.headers.get("Cookie")?.split('; ').find(row => row.startsWith('jwt='))?.split('=')[1] || '';
    const servicesData = await getAllServices(token);

    return {
        services: servicesData,
        deviceName: deviceName
    };
}

export async function action({
    request
} : ActionFunctionArgs) {
    const body = await request.formData();

    console.log(body);
    const token = request.headers.get("Cookie")?.split('; ').find(row => row.startsWith('jwt='))?.split('=')[1] || '';

    return fetch(`http://${API_PATH}/devices/${JSON.parse(body.get("deviceName"))}/addService`, {
        method: "POST",
        credentials: "include", // Permet d'accepter les cookies HTTPOnly du backend
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`, // Ajoute le token JWT dans l'en-tête Authorization
        },
        body: body.get("serviceId"),
    }).then((response) => {
        if (response.ok) {
            return redirect(`/device?name=${JSON.parse(body.get("deviceName"))}`)
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

export default function AddDevice() {
    const { services, deviceName } = useLoaderData<{ services: Service[], deviceName: string }>();

    return (
        <div className="w-full mt-8 flex justify-center">
            <DeviceServiceAdd services={services} deviceName={deviceName}/>
        </div>
    );

}