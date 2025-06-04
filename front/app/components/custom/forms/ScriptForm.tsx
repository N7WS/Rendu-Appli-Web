import { useForm } from "react-hook-form";
import { scriptSchema } from "./ScriptSchema";
import { z } from "zod";
import { Button } from "~/components/ui/button";
import { Input } from "~/components/ui/input";

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



export default function ScriptForm() {

    /** Création du form pour l'ajout d'un script */
    const form = useForm<z.infer<typeof scriptSchema>>({
        resolver: zodResolver(scriptSchema),
        defaultValues: { // Très important de définir les valeurs par défaut
            name: "",
            path: "",
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
                    name="name"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Nom du script</FormLabel>
                            <FormControl>
                                <Input {...field} className="w-96" />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />

                <FormField
                    control={form.control}
                    name="path"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Chemin du script</FormLabel>
                            <FormControl>
                                <Input {...field} className="w-96" />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <Button type="submit" className="text-xl font-bold h-12 w-48  bg-blue-400 hover:bg-blue-600">
                    Ajouter le script
                </Button>
                </form>
                </Form>
        </CardContent>
    </Card>
)
}