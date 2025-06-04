import { ServerCogIcon, ServerCrashIcon, ServerOffIcon } from "lucide-react";

type ServiceSelectProps = {
    name: string;
    port: number;
    onClick?: (arg? : any) => void;
    selected: boolean;
};

export default function ServiceSelectCard({name, port, onClick, selected}: ServiceSelectProps) {
    let border = selected ? "border-gray-500" : "border-gray-200";
    return (
        <div key={name} className={`flex items-center gap-4 bg-white ${border} border-2 border-solid p-4 mt-2 w-full h-16 rounded-xl justify-between`} onClick={onClick}>
            <div className="flex flex-col">
                <p className="text-xl">{name} : {port}</p>
            </div>
        </div>
    );
}
