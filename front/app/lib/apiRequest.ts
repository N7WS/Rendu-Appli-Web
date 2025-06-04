/** Module avec l'ensemble des requetes API les plus courante */

import { Script, Service } from "./utils";

export async function getAllServices(token: string): Promise<Service[]> {
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

    return await services.json();
}

export async function getAllScripts(token: string): Promise<Script[]> {
    const scripts = await fetch("http://localhost:8080/scripts", {
        method: "GET",
        credentials: "include", // Permet d'accepter les cookies HTTPOnly du backend
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`, // Ajoute le token JWT dans l'en-tête Authorization
        },
    });
    if (!scripts.ok) {
        throw new Response(scripts.statusText, {
            status: scripts.status,
            statusText: scripts.statusText,
        });
    }

    return await scripts.json();
}
