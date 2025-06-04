import { useLoaderData } from "@remix-run/react";
import { useEffect, useState } from 'react';
import DeviceCard from "~/components/custom/device-card";
import { Accordion, AccordionContent, AccordionItem, AccordionTrigger } from "~/components/ui/accordion";
import { Button } from "~/components/ui/button";
import { ScrollArea } from "~/components/ui/scroll-area";

// TODO : remove when transforming card to component
import { LoaderFunctionArgs } from "@remix-run/node";
import { ServerCogIcon, ServerCrashIcon, ServerOffIcon } from "lucide-react";
import { CartesianGrid, Line, LineChart, ResponsiveContainer, Tooltip, XAxis, YAxis } from "recharts";

import { TextSelect } from "lucide-react";
import { API_PATH } from "~/root";

var socket : WebSocket | undefined;

import { Device } from "~/lib/utils";

export async function loader({ request }: LoaderFunctionArgs) {

	// Get API_PATH from environment variables

	const token = request.headers.get("Cookie")?.split('; ').find(row => row.startsWith('jwt='))?.split('=')[1] || '';

	console.log("test : ", `http://${API_PATH}/devices`);

	const devices = await fetch(
		`http://${API_PATH}/devices`,
		{
			method: "GET",
			credentials: "include",
			headers: {
				"Content-Type": "application/json",
				"Authorization": `Bearer ${token}`,
			},
		}
	);
	if (!devices.ok) {
		throw new Response("Failed to fetch devices", { status: 500 });
	}

	const devicesData: Device[] = await devices.json();

	return { devices: devicesData };
}

function findRooms(devices: Device[]) {
	let rooms: string[] = [];
	for (const device of devices) {
		let room = device.room;
		if (!rooms.includes(room)) {
			rooms.push(room);
		}
	}
	return rooms;
}

interface HealthReport {
	deviceName: string;
	timestamp: number;
	ping: number; // ms
	ramUsage: number; // Mo
	cpuUsage: number; // %
}

export default function Index() {
	const { devices } = useLoaderData<{ devices: Device[] }>();
	const rooms: string[] = findRooms(devices);
	const [selectedDevice, setSelectedDevice] = useState<Device | null>(null);
	const [healthReport, setHealthReport] = useState<HealthReport[] | null>(null);

	console.log(devices);

	useEffect(() => {
		if (!selectedDevice) return;

		// Clean the health report when a new device is selected
		setHealthReport(null);
		console.log("Selected device:", selectedDevice.name);
		console.log("Cleaning health report");
		console.log("New health report:", healthReport);

		if (socket === undefined) {
			socket = new WebSocket(`ws://${API_PATH}/ws`);
		}

		socket.onopen = () => {
			console.log("WebSocket connection established");
			socket?.send(JSON.stringify({ deviceName: selectedDevice.name }));
		};
		socket.onmessage = (event) => {
			if (JSON.parse(event.data).deviceName === selectedDevice.name) {
				setHealthReport((prevReports) => {
					const newReport: HealthReport = JSON.parse(event.data);
					if (prevReports) {
						return [...prevReports, newReport];
					} else {
						return [newReport];
					}
				});
				console.log("Message received from server:", event.data);
			}
		}
		socket.onclose = () => {
			console.log("WebSocket connection closed");
		};
	}, [selectedDevice]);

	const handleSelection = (device: Device) => {
		setSelectedDevice(device);
	};

	const n : number = 50;

	function getLastNReports(reports: HealthReport[], n: number): HealthReport[] {
		return reports.slice(Math.max(reports.length - n, 0), reports.length);
	}

	function formatTimestamp(timestamp: number): string {
		const date = new Date(timestamp * 1000);
		const options: Intl.DateTimeFormatOptions = {
			hour: 'numeric',
			minute: 'numeric',
			second: 'numeric',
		};
		return date.toLocaleString('fr-FR', options);
	}

	return (
		<div className="flex flex-row justify-between px-16 pt-8 gap-8">

			{/* MACHINE SELECTION */}
			<div className="flex w-1/2 items-start overflow-hidden">
				<ScrollArea className="h-[40rem] w-full rounded-xl border p-4">
					<Accordion type="single" collapsible className="w-full">
						{rooms.map((room: string) => (
							<AccordionItem key={room} value={room}>
								<AccordionTrigger className="font-bold text-lg">{room}</AccordionTrigger>
								<AccordionContent>
									<div className="flex flex-col gap-2 w-full">
										{devices
											.filter((device) => device.room === room)
											.map((device: Device) => (
												<DeviceCard
													key={device.name}
													name={device.name}
													cpu={device.cpuName}
													ram={device.ramSize / 1024}
													room={device.room}
													status={device.status}
													lastUpdate={formatTimestamp(healthReport ? healthReport[healthReport.length - 1]?.timestamp ?? 0 : 0)}
													onClick={() => handleSelection(device)}
													selected={device.name === selectedDevice?.name}
												/>
											))}
									</div>
								</AccordionContent>
							</AccordionItem>
						))}
					</Accordion>
				</ScrollArea>
			</div>

			{/* SELECTED MACHINE */}
			<div className="w-1/2 justify-start">
				<div className="flex row items-center gap-2 w-full bg-blue-400 rounded-t-xl p-2">
					{selectedDevice?.status === "ONLINE" ? (
						<ServerCogIcon className="size-8 stroke-[2.5px] stroke-white" />
					) : selectedDevice?.status === "OFFLINE" ? (
						<ServerOffIcon className="size-8 stroke-[2.5px] stroke-white" />
					) : (
						<ServerCrashIcon className="size-8 stroke-[2.5px] stroke-white" />
					)}
					<p className="text-white font-extrabold">{selectedDevice?.name ?? "Not selected - please select a machine"}</p>
				</div>
				<div className="border-x-2 border-b-2 w-full border-grey-400 rounded-b-xl">
				{(selectedDevice === null) ? (
						<div className="flex w-full h-96 justify-center  bg-slate-100 rounded-b-xl">
							<div className="flex flex-col gap-4 justify-center items-center">
								<TextSelect className="h-40 w-40 stroke-blue-400" />
								<p className="text-blue-400 font-extrabold text-2xl">Veuillez selectionner une machine</p>
							</div>
						</div>
					) : (
						<div className="flex flex-col justify-center items-center pt-6 gap-8 pb-4">
						{healthReport && healthReport.length > 0 && (
							<div className="flex flex-row gap-4 w-full px-8">
							<div className="flex flex-col w-1/2">
								<p className="pt-[1em] font-semibold">CPU Usage : </p>
								{/* Ajout d'un padding à gauche si moins de n points */}
								{(() => {
									const reports = getLastNReports(healthReport, n);
									const padCount = n - reports.length;
									const paddedReports = [
										...Array(padCount).fill({
											timestamp: reports[0]?.timestamp ?? 0,
											cpuUsage: null,
											ramUsage: null,
											ping: null,
										}),
										...reports,
									];
									return (
										<ResponsiveContainer width="100%" height={300}>
											<LineChart data={paddedReports}>
												<CartesianGrid strokeDasharray="3 3" />
												<XAxis tickFormatter={formatTimestamp} dataKey="timestamp" />
												<YAxis />
												<Tooltip />
												<Line type="monotone" dataKey="cpuUsage" stroke="#60a5fa" dot={true} strokeWidth={2} animateNewValues={false} isAnimationActive={false} />
											</LineChart>
										</ResponsiveContainer>
									);
								})()}
							</div>
							<div className="flex flex-col w-1/2">
								<p className="pt-[1em] font-semibold">RAM Usage : </p>
								{/* Ajout d'un padding à gauche si moins de n points */}
								{(() => {
									const reports = getLastNReports(healthReport, n);
									const padCount = n - reports.length;
									const paddedReports = [
										...Array(padCount).fill({
											timestamp: reports[0]?.timestamp ?? 0,
											cpuUsage: null,
											ramUsage: null,
											ping: null,
										}),
										...reports,
									];
									return (
										<ResponsiveContainer width="100%" height={300}>
											<LineChart data={paddedReports}>
												<CartesianGrid strokeDasharray="3 3" />
												<XAxis tickFormatter={formatTimestamp} dataKey="timestamp" />
												<YAxis />
												<Tooltip />
												<Line type="monotone" dataKey="ramUsage" stroke="#60a5fa" dot={true} strokeWidth={2} animateNewValues={false} isAnimationActive={false} />
											</LineChart>
										</ResponsiveContainer>
									);
								})()}
							</div>
							</div>
						)}
						{selectedDevice && (
							<a href={`/device?name=${selectedDevice.name}`}>
								<Button type="button" className="w-40 bg-blue-400 hover:bg-blue-600">Consulter la machine</Button>
							</a>
						)}
					</div>
					)
					}
				</div>
			</div>
		</div>
	);
}