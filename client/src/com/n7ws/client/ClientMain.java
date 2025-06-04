package com.n7ws.client;

public class ClientMain {

    public static void main(String[] args) {

        Client client = new Client(args.length > 0 ? args[0] : "http://localhost:8080");

        System.out.println("Client application started on port : " + client.getPath());

        try {
            // Init : send Device config
            boolean hasConnected = false;
            while (!hasConnected) {
                // System.out.println("Client connecting...");
                hasConnected = client.sendDeviceConfig();
            }
            System.out.println("Client connected");

            boolean requestTask;
            while (true) {
                // sendHealthInfo (heartbeat)
                // System.out.println("Sending HealthInfo...");
                requestTask = client.sendHealthInfo();

                if (requestTask) {
                    System.out.println("Requesting a task...");
                    Task toDo = client.resquestTask();
                    System.out.println(toDo.name());
                    System.out.println(toDo.script_path());

                    ProcessBuilder pb = new ProcessBuilder();
                    pb.command("sh", toDo.script_path());
                    Process process = pb.start();

                    int exitCode = process.waitFor();
                    if (exitCode == 0) {
                        System.out.println("Task " + toDo.name() + " executed successfully.");
                    } else {
                        System.err.println("Task " + toDo.name() + " execution failed with exit code: " + exitCode);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

}
