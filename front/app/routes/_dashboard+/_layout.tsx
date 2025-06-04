import { LoaderFunction, LoaderFunctionArgs } from "@remix-run/node";
import { Outlet, useLoaderData } from "@remix-run/react";

import { ChevronDown, LogOut, Menu } from 'lucide-react';

import { SidebarProvider, SidebarTrigger } from "~/components/ui/sidebar";
import AppSidebar from "~/components/custom/app-sidebar";

import N7WSlogo from "~/public/N7WS.png";
import profil from "~/public/profil.jpg";
import { API_PATH } from "~/root";

interface UserInfo {
  uid: string;
  firstname: string;
  lastname: string;
  admin: boolean;
  password: string;
}

export async function loader({ request }: LoaderFunctionArgs) {
  const token = request.headers.get("Cookie")?.split('; ').find(row => row.startsWith('jwt='))?.split('=')[1] || '';
	const user = await fetch(
		`http://${API_PATH}/users/me`, {
			method: "GET",
			credentials: "include", // Permet d'accepter les cookies HTTPOnly du backend
			headers: {
				"Content-Type": "application/json",
				"Authorization": `Bearer ${token}`, // Ajoute le token JWT dans l'en-tête Authorization
			},
		}
	);

  if (!user.ok) {
    throw new Response("Failed to fetch user data", { status: 500 });
  }

  var userData: UserInfo = {
    uid: "dummy-uid",
    firstname: "-------",
    lastname: "-------",
    admin: false,
    password: "",
  };

  try {
    userData = await user.json();
  } catch (error) {
    console.error("Error parsing user data:", error);
  }

	return { user: userData };
}

export default function HeaderLayout() {
  const me = useLoaderData<{ user: UserInfo }>();
  return (
    <SidebarProvider defaultOpen={false}>
      <main className="w-full">
        {/* DEBUT DU HEADER */}
        {/* TODO : transfo en composant*/}
        <AppSidebar />
        <div className="flex justify-between items-center h-[4em] w-full px-4 border-[1px] border-solid">
          <div className="flex gap-4 justify-center items-center">
            <a href="/board/" className="cursor-pointer">
              <img alt="logo" src={N7WSlogo} className="w-40"/>
            </a>
            <SidebarTrigger className="h-8 w-8" />
          </div>
          <div className="flex items-center gap-[1.5rem] text-[2em] shrink-0">
            <img
              alt="pfp"
              src={profil}
              className="h-[2em] rounded-[50%] py-[0.5rem]"
            />
            <a>{me.user.firstname} {me.user.lastname}</a>
            <a href="/login" className="h-full">
              <LogOut className="w-[1.15em] h-[1.15em] stroke-red-500 stroke-2" />
            </a>
          </div>
        </div>
        {/* FIN DE HEADER */}

        <Outlet />
      </main>
    </SidebarProvider>
  
  );
}