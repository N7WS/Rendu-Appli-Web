
import { Outlet } from "@remix-run/react";
import serverImage from "~/public/login/server.png";
import N7WSlogo from "~/public/N7WS.png";


export default function Login() {
    return (
        <div className="flex flex-row w-full">
            <img alt="asset server" src={serverImage} className="h-screen"/>
            <div className="flex flex-col w-full items-center py-40">
                <img alt="logo" src={N7WSlogo} className="w-72" />
                <Outlet />
            </div>
        </div>
    )
};