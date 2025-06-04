

import { Separator } from "~/components/ui/separator";
import { Wifi, Wrench, ChartArea, CircleCheck, CircleEllipsis, CircleX, PlusCircleIcon } from 'lucide-react';

import { ActionFunctionArgs, LoaderFunctionArgs, createCookie } from "@remix-run/node";
import { redirect, useActionData, useLoaderData } from "@remix-run/react";
import { ScrollArea } from "~/components/ui/scroll-area"
import ScriptForm from "~/components/custom/forms/ScriptForm";
import ScriptCard from "~/components/custom/script-card";

import { Script } from "~/lib/utils";
import { Button } from "~/components/ui/button";
import { Input } from "~/components/ui/input";

import { getAllScripts } from "~/lib/apiRequest";


export async function loader({
    request,
}: LoaderFunctionArgs) {
    const token = request.headers.get("Cookie")?.split('; ').find(row => row.startsWith('jwt='))?.split('=')[1] || '';

    const scriptsData = await getAllScripts(token);
    return { scripts: scriptsData };
}

export default function Index() {
    
	const { scripts } = useLoaderData<{ scripts: Script[] }>();

    return (
        <div className="flex flex-row justify-between px-16 pt-8 gap-8"> 
            
            {/* Haut */}
            <div className="h-full w-1/2 flex flex-col ">
                <div className="w-full flex flex-col ">
                    <div className="flex row items-center gap-2 w-full bg-blue-400 rounded-t-xl p-2">
                        <div className="flex items-center gap-2 justify-start p-2 size-10">
                            <Wrench className="size-8 stroke-[2px] stroke-white"/>
                        </div>
                        <p className="text-white text-xl font-extrabold">Scripts</p>
                    </div>
                    <div className="flex flex-col border-grey-400 border rounded-b-xl">
                        <div className="flex flex-row gap-4 justify-between items-end m-4 ">
                            <Input className="h-10" placeholder="Rechercher un script..."/>
                            <a href="/scripts/add"><Button className="text-xl h-12 bg-blue-400 hover:bg-blue-600"><PlusCircleIcon className="h-6 w-6"/>Ajouter un script</Button></a>
                        </div>
                        <ScrollArea className="h-[40rem] w-full rounded-b-xl px-4">
                        <div className="flex flex-col gap-2 w-full px-2">
                        {scripts.map((script: Script) => (
                            <ScriptCard script={script}/>
                        ))}
                        </div>
                        </ScrollArea>
                    </div>
                </div>        
            </div>
        </div>    
    )
};