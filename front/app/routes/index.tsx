import { Button } from "~/components/ui/button";
import N7WSlogo from "~/public/N7WS.png";


export default function Index() {

    return (
        <div className="flex flex-col justify-start px-4 mt-2 gap-8">
            <img alt="logo" src={N7WSlogo} className="w-40"/>
            <div className="w-full flex flex-col items-center justify-center px-16 pt-8 gap-8">
                <h1>Welcome to N7WS</h1>
                <p className="h-24">Please connect to Access N7WS !</p>
                <a href="/login">
                    <Button className="w-56 h-12 font-bold text-xl bg-blue-400 hover:bg-blue-600">
                        Login
                    </Button>
                </a>
				<a href="/register">
                    <Button className="w-56 h-12 font-bold text-xl bg-blue-400 hover:bg-blue-600">
                        Register
                    </Button>
                </a>
            </div>
        </div>
    );
}