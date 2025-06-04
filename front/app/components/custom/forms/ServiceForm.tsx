import { z } from 'zod';
import { zodResolver } from "@hookform/resolvers/zod";
import { useEffect, useState } from "react";
import { useForm } from "react-hook-form";

import { serviceSchema } from "./ServiceSchema";

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
    FormMessage,
  } from "~/components/ui/form"

import { Button } from "~/components/ui/button";
import { Input } from "~/components/ui/input";
import { MultiSelect } from '~/components/ui/multiple-selector';

import { PlusCircle } from 'lucide-react';

import { Script } from "~/lib/utils";

export default function ServiceForm({scripts}: {scripts: Script[]}) {

    /** Création du form pour l'ajout d'un service */
    const form = useForm<z.infer<typeof serviceSchema>>({
        resolver: zodResolver(serviceSchema),
        defaultValues: {
            name: "",
            port: 0,
            scriptsId: [],
        }
    })

    /** Variable stockant les choix du multiselect */
    const [selectedScript, setSelectedScript] = useState<string[]>([])

    const scriptList = scripts.map((script) => ({
        value: script.uid,
        label: script.name,
    }));
    return (
        <Card className='w-[36rem]'>
        <CardHeader className="flex justify-center w-full bg-blue-400 rounded-t-xl p-2 px-12 h-16">
            <CardTitle className="flex items-center gap-3 text-white text-xl font-extrabold"><PlusCircle className="w-8 h-8"/>Ajouter un service</CardTitle>
        </CardHeader>
        <CardContent>
        <Form {...form}>
        <form method="post" className="flex flex-col gap-6 items-center pt-10" >
            <FormField
                control={form.control}
                name="name"
                render={({ field }) => (
                    <FormItem>
                        <FormLabel>Nom du service</FormLabel>
                        <FormControl>
                            <Input {...field} className="w-96" />
                        </FormControl>
                        <FormMessage />
                    </FormItem>
                )}
            />

            <FormField
                control={form.control}
                name="port"
                render={({ field }) => (
                    <FormItem>
                        <FormLabel>Port du service</FormLabel>
                        <FormControl>
                            <Input 
                                {...field} 
                                type='number'
                                value={field.value ?? ''}
                                onChange={(e) => {
                                    const valueAsNumber = e.target.value === '' ? undefined : Number(e.target.value);
                                    field.onChange(valueAsNumber ?? null);
                                }}
                                className="w-96" />
                        </FormControl>
                        <FormMessage />
                    </FormItem>
                )}
            />

            <FormField
                control={form.control}
                name="scriptsId"
                render={({ field }) => (
                    <FormItem>
                        <FormLabel>Scripts associés</FormLabel>
                        <FormControl>
                            <div>
                                <input type="hidden" name="scriptsId" value={JSON.stringify(field.value)} />
                                <MultiSelect
                                    className='w-96'
                                    options={scriptList}
                                    value={field.value} 
                                    onValueChange={field.onChange} 
                                    defaultValue={field.value}
                                    placeholder="Selectionner des scripts"
                                    variant="default"
                                    maxCount={5}
                                />
                            </div>
                        </FormControl>
                        <FormMessage />
                    </FormItem>
                )}
            />
            <Button type="submit" className="text-xl font-bold h-12 w-48  bg-blue-400 hover:bg-blue-600">
                Ajouter le service
            </Button>
        </form>
        </Form>
        </CardContent>
        </Card>
    )
}