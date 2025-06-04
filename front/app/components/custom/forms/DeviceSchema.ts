import { z, string } from "zod";

export const deviceSchema = z.object({
    name: z.string(),
    status: z.enum(["ONLINE", "OFFLINE", "DISABLE"]),
    room: z.string(),
    cpuName: z.string(),
    cpuCores: z.number(),
    cpuFreq: z.number(),
    ramSize: z.number(),
    ramFreq: z.number(),

    // Utilisation des UID pour lier les services
    services: z.array(string())
})