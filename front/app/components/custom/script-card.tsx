
import { Separator } from "~/components/ui/separator";
import { Wifi, Wrench, ChartArea, CircleCheck, CircleEllipsis, CircleX, TrashIcon } from 'lucide-react';
import { Service } from "~/lib/utils";

import { Script } from "~/lib/utils";
import { Button } from "../ui/button";
import { API_PATH } from "~/root";

export default function ScriptCard({script}: {script: Script}) {
    return (
        <div key={script.uid} className={`flex items-center justify-between gap-4 bg-white  border-2 border-solid p-4 w-full h-28 rounded-xl`} >
            <div className="flex flex-row">
            <div className="flex items-center gap-2 justify-center">
                <Wrench className="size-8 stroke-[2px]"/>
                    </div>
            <div className="flex flex-col px-10">             
                <p className="text-2xl justify-right">{script.name}</p>
                <p className="text-base text-stone-400 justify-right">{script.path}</p>
                {/* Affiche les services qui sont associé au script */}
                {/* {(script.services.length > 0) ? 
                    <div className="flex flex-row gap-2">
                        <p>Associé aux services :</p>
                        {script.services.map((service) => (
                            <span key={service.name} className="text-sm text-blue-500">{service.name}</span>
                        ))}
                    </div> :
                    <p>Aucune service associé</p>  
                } */}
            </div>
            </div>
            <Button
                // TODO: Move this to an action to handle the errors properly
                className="bg-gray-100 hover:bg-red-300 text-red-600 h-8 w-6 justify-center self-center"
                onClick={async () => {
                    const response = await fetch(`http://${API_PATH}/scripts/${script.uid}/delete`, {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                        },
                    });
                    window.location.reload();
                }
                }
            >
                <TrashIcon className="size-5" />
            </Button>
        </div>
    );
}
