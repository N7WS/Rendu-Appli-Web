import { LoaderFunctionArgs } from "@remix-run/node"
import { useLoaderData } from "@remix-run/react";
import { DatabaseZap, Plus, PlusCircle, PlusCircleIcon } from "lucide-react"
import { Input } from "~/components/ui/input";
import { Button } from "~/components/ui/button"
import { Service } from "~/lib/utils";
import { ScrollArea } from "~/components/ui/scroll-area";
import ServiceCard from "~/components/custom/service-card";

export async function loader( { request }: LoaderFunctionArgs) {
    const token = request.headers.get("Cookie")?.split('; ').find(row => row.startsWith('jwt='))?.split('=')[1] || '';

    const services = await fetch("http://localhost:8080/services", {
        method: "GET",
        credentials: "include", // Permet d'accepter les cookies HTTPOnly du backend
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`, // Ajoute le token JWT dans l'en-tête Authorization
        },
    });
    if (!services.ok) {
        throw new Response(services.statusText, {
            status: services.status,
            statusText: services.statusText,
        });
    }

    const servicesData : Service[] = await services.json();
    return { services: servicesData };
}

export default function services() {

    const { services } = useLoaderData<{ services: Service[] }>();

    return (
        <div className="flex flex-row justify-between px-16 pt-8 gap-8"> 
            
            {/* Haut */}
            <div className="h-full w-1/2 flex flex-col ">
                <div className="w-full flex flex-col ">
                    <div className="flex row items-center gap-2 w-full bg-blue-400 rounded-t-xl p-2">
                        <div className="flex items-center gap-2 justify-start p-2 size-10">
                            <DatabaseZap className="size-8 stroke-[2px] stroke-white"/>
                        </div>
                        <p className="text-white text-xl font-extrabold">Services</p>
                    </div>
                    <div className="flex flex-col border-grey-400 border rounded-b-xl">
                        <div className="flex flex-row gap-4 justify-between items-end m-4 ">
                            <Input className="h-10" placeholder="Rechercher un service..."/>
                            <a href="/services/add"><Button className="text-xl h-12 bg-blue-400 hover:bg-blue-600"><PlusCircleIcon className="h-6 w-6"/>Ajouter un service</Button></a>
                        </div>
                        <ScrollArea className="h-[40rem] w-full rounded-b-xl px-4">
                        <div className="flex flex-col gap-2 w-full px-2">
                        {services.map((service: Service) => (
                            <ServiceCard service={service}/>
                        ))}
                        </div>
                        </ScrollArea>
                    </div>
                </div>        
            </div>
        </div>   
    )
}