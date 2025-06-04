import { useLoaderData } from "@remix-run/react";
import { useSearchParams } from "@remix-run/react";
import { useRef, useState } from 'react';
import { Button } from "~/components/ui/button";
import { Separator } from "~/components/ui/separator";
import { ScrollArea } from "~/components/ui/scroll-area";
import ServiceSelectCard from "~/components/custom/service-select-card";

// TODO : remove when transforming card to component
import { 
	Wifi,
	Wrench,
	ChartArea, 
	CircleCheck,
	CircleEllipsis,
	CircleX, 
	PlusCircleIcon, 
	RocketIcon
} from 'lucide-react';

import DummyGraph from "~/public/dummy/dummy-graph.png";
import { Breadcrumb, BreadcrumbItem, BreadcrumbLink, BreadcrumbList, BreadcrumbPage, BreadcrumbSeparator } from "~/components/ui/breadcrumb";
import { API_PATH } from "~/root";

import type { Device, Script, Service } from "~/lib/utils";
import { getAllScripts } from "~/lib/apiRequest"

export async function loader({ request }: { request: Request }) {
	const url = new URL(request.url);
	const name = url.searchParams.get("name");
	const token = request.headers.get("Cookie")?.split('; ').find(row => row.startsWith('jwt='))?.split('=')[1] || '';

	let device: Device | null = null;
	let scripts: Script[] | null | undefined = null;

	await fetch(`http://${API_PATH}/devices/${name}`, {
		method: "GET",
		credentials: "include", // Permet d'accepter les cookies HTTPOnly du backend
		headers: {
			"Content-Type": "application/json",
			"Authorization": `Bearer ${token}`, // Ajoute le token JWT dans l'en-tête Authorization
		},
	}).then(async (response) => {
		if(response.ok) {
			device = await response.json();
		} else {
			throw new Response(response.statusText, {
				status: response.status,
				statusText: response.statusText,
			});
		}
	}).catch((error) => {
		console.log(error.message);
		let top_msg;
		let bot_msg;
		if (error.message === "fetch failed") {
			top_msg = "Server unreachable";
			bot_msg = error.msg;
		} else if (error.message === "Unexpected end of JSON input") {
			top_msg = "Invalid device name";
			bot_msg = "Device name has no match";
		}
		else {
			top_msg = error.msg;
			bot_msg = error.msg;
		}
        throw new Response(bot_msg, {
            status: error.status,
            statusText: top_msg,
        });
    });
	return {device, token};
}

async function sendTask(token:string, deviceName:string, scriptName:string, scriptPath:string) {
    try {
		const body = JSON.stringify({
			name: scriptName,
			script_path: scriptPath
		});

		const response = await fetch(`http://${API_PATH}/devices/${deviceName}/addTask`, {
		method: 'POST',
		credentials: "include", // Permet d'accepter les cookies HTTPOnly du backend
		headers: {
			'Content-Type': 'application/json',
			"Authorization": `Bearer ${token}`, // Ajoute le token JWT dans l'en-tête Authorization
		},
		body: body, // or scriptPath directly if plain string expected
		});

		if (!response.ok) {
			throw new Error(`HTTP error: ${response.status}`);
		}

		const text = await response.text();
		const result = text === 'true';

		return result; // caller can use this to update state
	} catch (error) {
		console.error('Add task failed:', error);
		return false; // fallback for failure
	}

}

export default function Device() {
	const { device, token } = useLoaderData<{ device : Device, token: string}>();

	const [selectedService, setSelectedService] = useState<Service | null>(null);

	const handleSelection = (service: Service) => {
		setSelectedService(service);
	};

	let status = device?.status ?? "PENDING";
	let status_color = (status === "ONLINE") ? "text-green-400" : ((status === "OFFLINE") ? "text-red-400" : "text-gray-400");

	return (
		<div className="h-full w-full flex flex-col px-16">
			{/* HAUT */}
			<div className="flex flex-row justify-center pt-4">
				<div className="w-1/2 flex flex-row justify-start px-4 py-2">
					<Breadcrumb>
						<BreadcrumbList>
							<BreadcrumbItem>
								<BreadcrumbLink href="/board/">{device?.room}</BreadcrumbLink>
							</BreadcrumbItem>
							<BreadcrumbSeparator />
							<BreadcrumbItem>
								<BreadcrumbPage>{device?.name}</BreadcrumbPage>
							</BreadcrumbItem>
						</BreadcrumbList>
					</Breadcrumb>
				</div>
			</div>

			{/* BAS */}
			<div className="h-full w-full flex flex-col items-center justify-start">
				<div className="w-1/2 justify-start flex flex-col gap-4"> {/* Gauche */}
					{/* Configuration */}
					<div className="w-full p-2">
						<div className="flex row items-center gap-2 w-full bg-blue-400 rounded-t-xl p-2">
							<Wifi className="size-8 stroke-[2px] stroke-white"/>
							<p className="text-white text-xl font-extrabold">Configuration</p>
						</div>
						<div className="border-x-2 border-b-2 w-full border-grey-400 rounded-b-xl p-8">
							<div className="flex flex-row items-center">
								<span className="text-black font-bold">Status : </span>
								{(device?.status ?? "PENDING") === "ONLINE" ? (
									<div className="flex items-center justify-start rounded-full px-1">
										<CircleCheck className="size-[1em] stroke-[2.5px] stroke-green-400" />
									</div>
								) : (device?.status ?? "PENDING") === "OFFLINE" ? (
									<div className="flex items-center justify-start rounded-full px-1">
										<CircleX className="size-[1em] stroke-[2.5px] stroke-red-400" />
									</div>) :
									<div className="flex items-center justify-start rounded-full px-1">
										<CircleEllipsis className="size-[1em] stroke-[2.5px] stroke-gray-400" />
									</div>
								}
								<span className={`font-semibold ${status_color}`}>{device?.status ?? "Unknown"}</span>
							</div>
							<p><span className="font-bold">CPU</span> : {device?.cpuName ?? "Unknown"}</p>
							<p><span className="font-bold">RAM</span> : {device?.ramSize ?? "Unknown"} Mo</p>

						</div>
					</div>
				</div>

				<div className="w-1/2 p-2"> {/* Droite */}
					<div className="flex row items-center gap-2 w-full bg-blue-400 rounded-t-xl p-2">
						<div className="flex items-center gap-2 justify-start rounded-full p-2 size-10">
							<Wrench className="size-8 stroke-[2px] stroke-white"/>
						</div>
						<p className="text-white text-xl font-extrabold">Déploiement</p>
					</div>
					<div className="flex flex-col justify-center gap-8 border-x-2 border-b-2 border-grey-400 w-full rounded-b-xl p-8">

						<div className="flex flex-row items-center gap-4">
							<div className="flex items-center gap-2 w-full">
									{device?.services?.length ? (
										<>
											<div className="flex flex-row gap-8 px-4 w-full">
												<p className="text-black font-bold text-xl">Services:</p>
												<div className="flex flex-col gap-2 w-full">
													<ScrollArea className="h-96 w-full rounded-xl border p-4">
														{device.services.map((service: Service) => (
															<div onClick={() => handleSelection(service)}>
																<ServiceSelectCard
																	name={service.name}
																	port={service.port}
																	onClick={() => handleSelection(service)}
																	selected={service.name === selectedService?.name}
																	></ServiceSelectCard>
																{/* <p>{service.name} : {service.port}</p> */}
															</div>
														))}
													</ScrollArea>
												</div>
											</div>
										</>
									) : (
										<p className="text-black font-bold">Aucun service déployé</p>
									)}
							</div>
						</div>
						<Separator className="w-full" />
						<div className="flex flex-row gap-4">
							<div className="flex">
								<div className="flex justify-end">
									<a href={"device/add?name="+device.name}><Button className="w-56 h-12 font-bold text-xl bg-blue-400 hover:bg-blue-600"><PlusCircleIcon />Ajouter un service</Button></a>
								</div>
							</div>
							<div className="flex flex-row items-center">
								{(selectedService != null) ?
									<Button
										className="w-56 h-12 font-bold text-xl bg-blue-400 hover:bg-blue-600"
										onClick={
											() => selectedService?.scripts.forEach(script => {
												sendTask(token, device.name, script.name, script.path);
											}
										)}
									>
										<RocketIcon /><a>Déployer le service</a>
									</Button>
									:
									<p className="text-xl font-semibold">Select a service to deploy.</p>
								}
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	);
}
