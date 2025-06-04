import { clsx, type ClassValue } from "clsx"
import { twMerge } from "tailwind-merge"

export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs))
}

export interface Service {
  uid: string
  name: string
  port: number
  scripts: Script[]
}

export interface Script {
  uid: string
  name: string
  path: string
}

enum DeviceState {
	ONLINE = "ONLINE",
	OFFLINE = "OFFLINE",
	DISABLED = "DISABLED"
}

export interface Device {
	name: string;
	status: DeviceState;
	room: string;
	cpuName: string;
	cpuCores: number;
	cpuFreq: number; // MHz
	ramSize: number; // Mo
	ramFreq: number; // MHz
	services: Service[];
}
