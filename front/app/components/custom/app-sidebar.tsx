import { ChevronDown, DatabaseZap, HardDrive, LogOut, Menu, Settings } from 'lucide-react';
import { DropdownMenu, DropdownMenuContent, DropdownMenuItem, DropdownMenuTrigger } from "~/components/ui/dropdown-menu";
import { Sidebar, SidebarContent, SidebarFooter, SidebarGroup, SidebarGroupContent, SidebarHeader, SidebarMenu, SidebarMenuButton, SidebarMenuItem, SidebarTrigger } from "~/components/ui/sidebar";

import kcLogo from "~/public/kc_logo.png";
import N7WS from "~/public/N7WS.png";

export default function AppSidebar() {
    return (
        <Sidebar side='left'>
        <SidebarHeader className='mb-4'>
          <SidebarMenu>
            <SidebarMenuItem>
                <div className='flex items-center justify-around'>
                    <img src={N7WS} className='h-8'/>
                    <SidebarTrigger className="h-12 w-12" />
                </div>            
            </SidebarMenuItem>
          </SidebarMenu>
        </SidebarHeader>
        <SidebarContent>
            <SidebarGroup>
                <SidebarGroupContent>
                    <SidebarMenu className='flex flex-col gap-2'>
                        <SidebarMenuItem>
                            <SidebarMenuButton className='h-10'>
                                <a href = "/board" className="flex ml-4 items-center gap-4">
                                    <HardDrive className="w-6 h-6 mr-2" />
                                    <span className='font-bold text-black text-lg'>Dashboard</span>
                                </a>
                            </SidebarMenuButton>
                        </SidebarMenuItem>
                        <SidebarMenuItem>
                            <SidebarMenuButton className='h-10'>
                                <a href = "/scripts" className="flex ml-4 items-center gap-4">
                                    <Settings className="w-6 h-6 mr-2" />
                                    <span className='font-bold text-black text-lg'>Scripts</span>
                                </a>
                            </SidebarMenuButton>
                        </SidebarMenuItem>
                        <SidebarMenuItem>
                            <SidebarMenuButton className='h-10'>
                                <a href = "/services" className="flex ml-4 items-center gap-4">
                                    <DatabaseZap className="w-6 h-6 mr-2" />
                                    <span className='font-bold text-black text-lg'>Services</span>
                                </a>
                            </SidebarMenuButton>
                        </SidebarMenuItem>

                        
                    </SidebarMenu>
                </SidebarGroupContent>
            </SidebarGroup>
        </SidebarContent>
        
        <SidebarFooter>
            <div className='flex gap-2 items-center justify-center mb-6'>
                <p>Developed by :</p>
                <img alt="logo" src={kcLogo} className="w-10"/>
            </div>
        </SidebarFooter>
        
      </Sidebar>
    )
}