import { ServerCogIcon, ServerCrashIcon, ServerOffIcon } from "lucide-react";

type DeviceCardProps = {
    name: string;
    cpu: string;
    ram: number;
    room: string;
    status: string;
    onClick?: (arg? : any) => void;
    selected: boolean;
    lastUpdate: string;
};

export default function DeviceCard({name, cpu, ram, room, status, onClick, selected, lastUpdate}: DeviceCardProps) {
    let border = selected ? "border-gray-500" : "border-gray-200";
    return (
        <div key={name} className={`flex items-center gap-4 bg-white ${border} border-2 border-solid p-4 w-full h-28 rounded-xl justify-between`} onClick={onClick}>
            <div className="flex flex-col">
                <p className="text-2xl">{name}</p>
                <p className="text-base text-stone-400">{cpu}</p>
                <p className="text-base text-stone-400">{ram && ram + "go RAM"}</p>
            </div>
            <div className="flex flex-col items-end gap-2">
                {status === "ONLINE" ? (
                    <div className="flex items-center gap-2 justify-start bg-blue-400 rounded-full p-2 size-10">
                        <ServerCogIcon className="size-8 stroke-[2.5px] stroke-white"/>
                    </div>
                    ) : status === "OFFLINE" ? (
                    <div className="flex items-center gap-2 justify-start bg-red-400 rounded-full p-2 size-10">
                        <ServerOffIcon className="size-8 stroke-[2.5px] stroke-white"/>
                    </div>) :
                    <div className="flex items-center gap-2 justify-start bg-gray-400 rounded-full p-2 size-10">
                        <ServerCrashIcon className="size-8 stroke-[2.5px] stroke-white"/>
                    </div>
                }
                <p className="text-base">Dernière actualisation : {lastUpdate}</p>
            </div>
        </div>
    );
}
