import { useForm } from "react-hook-form";
import { deviceServiceAddSchema } from "./DeviceServiceAddSchema";
import { z } from "zod";
import { Button } from "~/components/ui/button";
import { Input } from "~/components/ui/input";

import {
    Select,
    SelectContent,
    SelectItem,
    SelectTrigger,
    SelectValue,
  } from "~/components/ui/select"

import {
    Card,
    CardContent,
    CardDescription,
    CardFooter,
    CardHeader,
    CardTitle,
} from "~/components/ui/card"

import { 
    Form, 
    FormControl, 
    FormField, 
    FormItem, 
    FormLabel, 
    FormMessage 
} from "~/components/ui/form";

import { zodResolver } from "@hookform/resolvers/zod";
import { PlusCircle } from "lucide-react";
import { Service } from "~/lib/utils";


export default function DeviceServiceAdd({services, deviceName}: {services: Service[], deviceName: string}) {
    /** Création du form pour l'ajout d'un script */
    const form = useForm<z.infer<typeof deviceServiceAddSchema>>({
        resolver: zodResolver(deviceServiceAddSchema),
        defaultValues: { // Très important de définir les valeurs par défaut
            serviceId: "",
        },
    })
    return (
    <Card className='w-[36rem]'>
        <CardHeader className="flex justify-center w-full bg-blue-400 rounded-t-xl p-2 px-12 h-16">
            <CardTitle className="flex items-center gap-3 text-white text-xl font-extrabold"><PlusCircle className="w-8 h-8"/>Ajouter un script</CardTitle>
        </CardHeader>
        <CardContent>
        <Form {...form}>
        <form method="post" className="flex flex-col gap-6 items-center pt-10">
                <FormField
                    control={form.control}
                    name="serviceId"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Service</FormLabel>
                            <FormControl>
                                <div>
                                    <input type="hidden" name="serviceId" value={field.value} />
                                    <Select
                                        value={field.value}
                                        onValueChange={field.onChange}
                                        defaultValue={field.value}
                                    >
                                        <SelectTrigger className="w-96">
                                            <SelectValue placeholder="Selectionner un service" />
                                        </SelectTrigger>
                                        <SelectContent>
                                            {services.map((service) => (
                                                <SelectItem key={service.uid} value={service.uid}>
                                                    {service.name}
                                                </SelectItem>
                                            ))}
                                        </SelectContent>
                                    </Select>
                                </div>
                            </FormControl>
                            <FormControl>
                                <input type="hidden" name="deviceName" value={JSON.stringify(deviceName)} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <Button type="submit" className="text-xl font-bold h-20 w-full bg-blue-400 hover:bg-blue-600">
                    Ajouter le service à <br />{deviceName}
                </Button>
                </form>
                </Form>
        </CardContent>
    </Card>
    )
}