import { zodResolver } from "@hookform/resolvers/zod"
import { useForm } from "react-hook-form"
import { z } from "zod"
import { useState } from "react";

import { Button } from "~/components/ui/button";
import { Input } from "~/components/ui/input";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "~/components/ui/form"

import { Eye, EyeOff } from "lucide-react";

import { loginSchema } from "~/components/custom/forms/LoginSchema";

export default function LoginForm() {

    /** Variable réactive pour montrer ou non le mot de passe dans l'input */
    const [isViewPassword, setIsViewPassword] = useState(false);

    /** Création du form de connexion d'un utilisateur */
    const form = useForm<z.infer<typeof loginSchema>>({
        resolver: zodResolver(loginSchema),
        defaultValues: { // Très important de définir les valeurs par défaut
            email: "",
            password: "",
        },
    })

    return(
        <div className="flex flex-col pt-10">
                <Form {...form}>
                    <form method="post" className="flex flex-col items-center space-y-8">
                        <FormField
                        control={form.control}
                        name="email"
                        render={({ field }) => (
                            <FormItem>
                            <FormLabel>Email</FormLabel>
                            <FormControl>
                                <Input {...field} className="w-80"/>
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
                        <Button type="submit" className="text-xl h-12 w-40 bg-blue-600 hover:bg-blue-800">Se connecter</Button>
                    </form>
                </Form>
                </div>
    )
}