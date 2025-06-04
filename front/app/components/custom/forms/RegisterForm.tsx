import { zodResolver } from "@hookform/resolvers/zod";
import { useState } from "react";
import { useForm } from "react-hook-form";
import { z } from "zod";

import { Button } from "~/components/ui/button";
import {
    Form,
    FormControl,
    FormField,
    FormItem,
    FormLabel,
    FormMessage,
} from "~/components/ui/form";
import { Input } from "~/components/ui/input";

import { Eye, EyeOff } from "lucide-react";

import { registerSchema } from "~/components/custom/forms/RegisterSchema";

export default function RegisterForm() {
    /** Variable réactive pour montrer ou non le mot de passe dans l'input */
    const [isViewPassword, setIsViewPassword] = useState(false);

    /** Création du form d'inscription d'un utilisateur */
    const form = useForm<z.infer<typeof registerSchema>>({
        resolver: zodResolver(registerSchema),
        defaultValues: { // Très important de définir les valeurs par défaut
            email: "",
            password: "",
            firstname: "",
            lastname: "",
        },
    })

    return (
        <div className="flex flex-col items-center">
            <Form {...form}>
                <form method="post" className="flex flex-col items-center pt-10">
                    <div className="grid grid-cols-2 gap-x-6 gap-y-4 mb-4">
                    <FormField
                        control={form.control}
                        name="firstname"
                        render={({ field }) => (
                            <FormItem>
                                <FormLabel>Prénom</FormLabel>
                                <FormControl>
                                    <Input {...field} className="w-80" />
                                </FormControl>
                                <FormMessage />
                            </FormItem>
                        )}
                    />

                    <FormField
                        control={form.control}
                        name="lastname"
                        render={({ field }) => (
                            <FormItem>
                                <FormLabel>Nom</FormLabel>
                                <FormControl>
                                    <Input {...field} className="w-80" />
                                </FormControl>
                                <FormMessage />
                            </FormItem>
                        )}
                    />

                    <FormField
                        control={form.control}
                        name="email"
                        render={({ field }) => (
                            <FormItem>
                                <FormLabel>Email</FormLabel>
                                <FormControl>
                                    <Input {...field} className="w-80" />
                                </FormControl>
                                <FormMessage />
                            </FormItem>
                        )}
                    />

                    <FormField
                        control={form.control}
                        name="password"
                        render={({ field }) => (
                            <FormItem>
                            <FormLabel>Mot de passe</FormLabel>
                                <FormControl>
                                    <div className="relative">
                                    <Input
                                        type={isViewPassword ? "text" : "password"}
                                        className="w-80"
                                        {...field}
                                    />

                                    {isViewPassword ? (
                                        <Eye
                                        className="absolute right-4 top-[25%] h-5 w-5 z-10 cursor-pointer text-gray-500"
                                        onClick={() => {
                                            setIsViewPassword(!isViewPassword)
                                        }}
                                        />
                                    ) : (
                                        <EyeOff
                                        className="absolute right-4 top-[25%] h-5 w-5 z-10 cursor-pointer text-gray-500"
                                        onClick={() => setIsViewPassword(!isViewPassword)}
                                        />
                                    )}
                                    </div>
                                </FormControl>
                            <FormMessage />
                            </FormItem>
                        )}
                    />
                    </div>
                    <Button type="submit" className="text-xl h-12 w-40 bg-blue-600 hover:bg-blue-800">S'inscrire</Button> 
                </form>
            </Form>
        </div>
    )
}