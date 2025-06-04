import { z } from 'zod';

export const deviceServiceAddSchema = z.object({
    serviceId: z.string({ message: "Veuillez selectionner un service" })
})
