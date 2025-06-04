import { z } from "zod";

export const registerSchema = z.object({
    firstname: z.string({ message: "Veuillez indiquer votre prénom" }).min(2, { message: "Le prénom doit faire au moins 2 caractères" }),
    lastname: z.string({ message: "Veuillez indiquer votre nom" }).min(2, { message: "Le nom doit faire au moins 2 caractères" }),
    email: z.string().email("Format d'adresse mail invalide"),
    password: z.string({ message: "Veuillez indiquer un mot de passe" }).min(8, { message: "Le mot de passe doit faire au moins 8 caractères" }),
});