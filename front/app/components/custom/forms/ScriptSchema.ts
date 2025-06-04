import { z } from 'zod';
import { zodResolver } from "@hookform/resolvers/zod"
import { useForm } from "react-hook-form"

export const scriptSchema = z.object({
    name: z.string(),
    path: z.string()
});