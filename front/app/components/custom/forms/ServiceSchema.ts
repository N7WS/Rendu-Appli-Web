import { string, z } from "zod";

export const serviceSchema = z.object({
    name: z.string({message: "Veuillez donner un nom au service"}),
    port: z.number({message: "Veuillez indiquer le port du service"}),

    // Utilisation des UID des machines pour les selectionner.
    scriptsId: z.array(string()).min(1, {message: "Veuillez selectionner une machine"})
})