import { Service } from "~/lib/utils";
import { Button } from "../ui/button";
import { API_PATH } from "~/root";
import { TrashIcon } from "lucide-react";

export default function ServiceCard({ service }: { service: Service }) {
    return (
        <div key={service.uid} className="flex items-center justify-between gap-4 bg-white border-2 border-solid p-4 w-full h-28 rounded-xl">
            <div className="flex flex-row">
                <div className="flex items-center gap-2 justify-center">
                    <span className="text-2xl font-bold">{service.name}</span>
                </div>
                <div className="flex flex-col px-10">
                    <p className="text-base text-stone-400">Port: {service.port}</p>
                    {/* Affiche les scripts associés au service */}
                    {(service.scripts.length > 0) ? 
                        <div className="flex flex-row gap-2 items-center">
                            <p>Scripts associés :</p>
                            {service.scripts.map((script) => (
                                <span key={script.uid} className="text-sm text-blue-500">{script.name}</span>
                            ))}
                        </div> :
                        <p>Aucun script associé</p>
                    }
                    {/* Affiche les machines associés au service */}
                    {/* {(service.devices.length > 0) ? 
                        <div className="flex flex-row gap-2">
                            <p>Machines associés :</p>
                            {service.devices.map((device) => (
                                <span key={device.name} className="text-sm text-blue-500">{device.name}</span>
                            ))}
                        </div> :
                        <p>Aucune machine associée</p>
                    } */}

                    {/* Delete service usin API:8080/{uid}/delete */}
                </div>
            </div>
            <Button
                // TODO: Move this to an action to handle the errors properly
                className="bg-gray-100 hover:bg-red-300 text-red-600 h-8 w-6 justify-center self-center"
                onClick={async () => {
                    const response = await fetch(`http://${API_PATH}/services/${service.uid}/delete`, {
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