import { z } from "zod";

export const loginSchema = z.object({
    email: z.string().email("Format d'adresse mail invalide"),
    password: z.string({message: "Veuillez indiquez un mot de passe"}).min(8, "Le mot de passe doit faire au moins 8 caractères"),
});