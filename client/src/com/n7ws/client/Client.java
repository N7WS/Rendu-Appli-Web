package com.n7ws.client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.List;
import java.time.Instant;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

// OSHI
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.GraphicsCard;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.PhysicalMemory;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;

import io.github.cdimascio.dotenv.Dotenv;

public class Client {

    HttpClient httpClient;

    private String path = "";

    final SystemInfo si = new SystemInfo();

    final HardwareAbstractionLayer hal = si.getHardware();

    public Client() {
        this("http://localhost:8080");
    }

    public Client(String path) {
        this.path = path;
        this.httpClient = HttpClient.newHttpClient();
    }

    public String getPath() {
        return this.path;
    }

    public String createDeviceJson() {
        // OS
        OperatingSystem os = si.getOperatingSystem();
        // CPU
        CentralProcessor cpu = this.hal.getProcessor();
        // RAM
        List<PhysicalMemory> ram_sticks = hal.getMemory().getPhysicalMemory();
        boolean physicalRamFound = !ram_sticks.isEmpty();
        int ramFreq = (physicalRamFound) ? (int) Math.round(ram_sticks.getFirst().getClockSpeed() / 1_000_000.0) : 0;

        String name = os.getNetworkParams().getHostName();

        // Parse the mapping.json file to get the room name
        String json = """
            [
                {
                    "name": "b118",
                    "devices": [
                        "b118-01"
                    ]
                },
                {
                    "name": "c201",
                    "devices": [
                        "basilic",
                        "behemot",
                        "cerbere",
                        "dragon",
                        "gobelin",
                        "gorgone",
                        "griffon",
                        "hippogriffe",
                        "hydre",
                        "manticore",
                        "minotaure",
                        "nymphe",
                        "pegase",
                        "phenix",
                        "pixie",
                        "succube",
                        "troll",
                        "wyvern"
                    ]
                },
                {
                    "name": "c202",
                    "devices": [
                        "galium",
                        "zirconium",
                        "polonium",
                        "uranium",
                        "cobalt",
                        "nickel",
                        "phosphore",
                        "azote",
                        "neon",
                        "carbone",
                        "bore",
                        "titane",
                        "oxygene",
                        "antimoine",
                        "iode",
                        "sodium",
                        "actinium",
                        "fluor"
                    ]
                },
                {
                    "name": "c203",
                    "devices": [
                        "apollinaire",
                        "baudelaire",
                        "brassens",
                        "demusset",
                        "ferre",
                        "gautier",
                        "hugo",
                        "lafontaine",
                        "lamartine",
                        "mallarme",
                        "maupassant",
                        "poe",
                        "prevert",
                        "rimbaud",
                        "sand",
                        "verlaine"
                    ]
                },
                {
                    "name": "c204",
                    "devices": [
                        "albator",
                        "bouba",
                        "calimero",
                        "candy",
                        "casimir",
                        "clementine",
                        "diabolo",
                        "esteban",
                        "goldorak",
                        "heidi",
                        "ladyoscar",
                        "maya",
                        "scoubidou",
                        "snorki",
                        "tao",
                        "zia"
                    ]
                },
                {
                    "name": "c205",
                    "devices": [
                        "aspicot",
                        "bulbizarre",
                        "carapuce",
                        "chenipan",
                        "hypotrempe",
                        "magicarpe",
                        "melofee",
                        "nidoran",
                        "piafabec",
                        "pikachu",
                        "psykokwak",
                        "rattata",
                        "rondoudou",
                        "roucool",
                        "salameche",
                        "taupiqueur"
                    ]
                },
                {
                    "name": "c206",
                    "devices": [
                        "luke",
                        "leia",
                        "r2d2",
                        "vador",
                        "solo",
                        "palpatine",
                        "yoda",
                        "kenobi",
                        "ewok",
                        "jabba",
                        "chewie",
                        "lando",
                        "dagobah",
                        "bobafett",
                        "z6po",
                        "ackbar"
                    ]
                },
                {
                    "name": "c208",
                    "devices": [
                        "borisov"
                    ]
                },
                {
                    "name": "c214",
                    "devices": [
                        "atanasoff",
                        "atkinson",
                        "babagge",
                        "backus",
                        "boole",
                        "carmack",
                        "colmerauer",
                        "dijkstra",
                        "gates",
                        "gosling",
                        "grove",
                        "hopper",
                        "jobs",
                        "kernighan",
                        "knuth",
                        "liskov",
                        "lovelace",
                        "moore",
                        "murdock",
                        "neumann",
                        "shannon",
                        "stallman",
                        "swartz",
                        "torvalds",
                        "turing",
                        "zemanek"
                    ]
                },
                {
                    "name": "c216",
                    "devices": [
                        "angel",
                        "banshee",
                        "bishop",
                        "cable",
                        "colossus",
                        "cyclope",
                        "dazzler",
                        "diablo",
                        "forge",
                        "gambit",
                        "havok",
                        "iceberg",
                        "jeangrey",
                        "jubele",
                        "magma",
                        "magneto",
                        "malicia",
                        "mystique",
                        "polaris",
                        "psylocke",
                        "rocket",
                        "serval",
                        "shadowcat",
                        "sunfire",
                        "tornade",
                        "xorn"
                    ]
                },
                {
                    "name": "c301",
                    "devices": [
                        "arryn",
                        "baratheon",
                        "bravoos",
                        "dorne",
                        "greyjoy",
                        "lannister",
                        "marcheursblancs",
                        "martell",
                        "meeren",
                        "portreal",
                        "sauvageons",
                        "snow",
                        "stark",
                        "targaryen",
                        "tully",
                        "tyrell",
                        "volantis",
                        "winterfell"
                    ]
                },
                {
                    "name": "c302",
                    "devices": [
                        "archimede",
                        "aston",
                        "copernic",
                        "darwin",
                        "descartes",
                        "edison",
                        "einsten",
                        "fermat",
                        "galilee",
                        "hawking",
                        "kepler",
                        "newton",
                        "ohm",
                        "pascal",
                        "socrate",
                        "tesla",
                        "volta",
                        "watt"
                    ]
                },
                {
                    "name": "c303",
                    "devices": [
                        "ablette",
                        "anguille",
                        "brochet",
                        "carpe",
                        "chevesne",
                        "gardon",
                        "goujon",
                        "omble",
                        "perche",
                        "tanche",
                        "truite",
                        "sandre",
                        "silure",
                        "vairon"
                    ]
                },
                {
                    "name": "c304",
                    "devices": [
                        "aragorn",
                        "arwen",
                        "bilbo",
                        "boromir",
                        "elrond",
                        "eomer",
                        "eowyn",
                        "faramir",
                        "frodon",
                        "galadriel",
                        "gandalf",
                        "gimli",
                        "legolas",
                        "merry",
                        "pippin",
                        "sam"
                    ]
                },
                {
                    "name": "c305",
                    "devices": [
                        "alose",
                        "bonite",
                        "corb",
                        "daurade",
                        "eperlan",
                        "fletan",
                        "gobie",
                        "mordocet",
                        "nerophis",
                        "orphie",
                        "palomete",
                        "rouget",
                        "sar",
                        "tacaud"
                    ]
                },
                {
                    "name": "c306",
                    "devices": [
                        "ader",
                        "bastie",
                        "bleriot",
                        "boeing",
                        "boucher",
                        "farman",
                        "garros",
                        "guynemer",
                        "latecoere",
                        "lindbergh",
                        "marvingt",
                        "mermoz",
                        "messerschmitt",
                        "saintexupery",
                        "voisin",
                        "wright"
                    ]
                },
                {
                    "name": "c308",
                    "devices": [
                        "c308-01",
                        "c308-02",
                        "c308-03",
                        "c308-04",
                        "c308-05",
                        "c308-06",
                        "c308-07",
                        "c308-08",
                        "c308-09",
                        "c308-10",
                        "c308-11",
                        "c308-12",
                        "c308-13",
                        "c308-14",
                        "c308-15"
                    ]
                },
                {
                    "name": "c309",
                    "devices": [
                        "c309-01",
                        "c309-02",
                        "c309-03",
                        "c309-04",
                        "c309-05",
                        "c309-06",
                        "c309-07",
                        "c309-08",
                        "c309-09",
                        "c309-10",
                        "c309-11",
                        "c309-12",
                        "c309-13",
                        "c309-14",
                        "c309-15"
                    ]
                },
                {
                    "name": "c311",
                    "devices": [
                        "www-tr"
                    ]
                }
            ]
        """;

        String room = "No Room";
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<RoomMapping> mappings = mapper.readValue(
                json,
                mapper.getTypeFactory().constructCollectionType(
                    List.class,
                    RoomMapping.class
                )
            );

            for (RoomMapping mapping : mappings) {
                if (mapping.devices().contains(name.replace(".enseeiht.fr", ""))) {
                    System.out.println("Found mapping for device: " + name);
                    room = mapping.name();
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading mapping.json: " + e);
        }

        Device device = new Device(
            name,                                                               // device name is hostname
            DeviceState.ONLINE,                                                 // status; if this could be sent, we're online
            room,                                                               // room
            cpu.getProcessorIdentifier().getName(),                             // cpu name
            cpu.getPhysicalProcessorCount(),                                    // cpu cores
            Integer.valueOf((int) Math.round(cpu.getMaxFreq() / 1_000_000.0)),  // cpu frequency, Mhz
            Integer.valueOf((int) Math.round(hal.getMemory().getTotal() / Math.pow(1024, 2))), // ram size (Mo)
            Integer.valueOf(ramFreq)                                            // frequency of the first ram stick (Mhz)
        );

        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(device);
        } catch (Exception e) {
            System.err.println(e);
        }
        return "{}";
    }

    public boolean sendDeviceConfig() {
        String json = createDeviceJson();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(path+"/devices/add"))
            .header("Content-Type", "application/json")
            .header("Authorization", Dotenv.load().get("SECRET_BYPASS"))
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .build();

        try {
            HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) { // succeeded
                String responseBody = response.body();
                return responseBody.equals("true");

            } else {
                System.err.println("Error: HTTP status" + response.statusCode());
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return false;
    }

    public boolean sendHealthInfo() {

        OperatingSystem os = si.getOperatingSystem();
        String deviceName = os.getNetworkParams().getHostName();

        CentralProcessor processor = si.getHardware().getProcessor();
        GlobalMemory memory = si.getHardware().getMemory();

        // CPU Load
        long[] loadTicksBefore = processor.getSystemCpuLoadTicks();
        try {
            Thread.sleep(1000);  // wait a second to measure CPU usage
        } catch (InterruptedException e) {
            System.err.println("Sleep interrupted: " + e);
        }
        Integer cpuLoad = Integer.valueOf((int) (processor.getSystemCpuLoadBetweenTicks(loadTicksBefore) * 100));

        // RAM Usage
        long totalMemory = memory.getTotal();
        Integer usedMemoryPercentage = Math.round((int) (((totalMemory - memory.getAvailable()) * 100.0) / totalMemory));

        Instant currentTimeStamp = Instant.now();

        Integer ping = Integer.valueOf(69);

        HealthInfo heartbeat = new HealthInfo(deviceName, currentTimeStamp, ping, cpuLoad, usedMemoryPercentage);

        String json = "{}";
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            json = mapper.writeValueAsString(heartbeat);
        } catch (Exception e) {
            System.err.println(e);
        }

        // Send healthInfo request
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(path+"/healthReports/"+ deviceName +"/add"))
            .header("Content-Type", "application/json")
            .header("Authorization", Dotenv.load().get("SECRET_BYPASS"))
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .build();

        try {
            HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) { // succeeded
                String responseBody = response.body();

                boolean receiveTask = responseBody.equals("true");
                return receiveTask;

            } else {
                System.err.println("Error: HTTP status" + response.statusCode());
            }
        } catch (Exception e) {
            System.err.println(e);
        }

        return false; // default: no task to request
    }

    public Task resquestTask() {

        OperatingSystem os = si.getOperatingSystem();
        String deviceName = os.getNetworkParams().getHostName();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(path+"/devices/" + deviceName + "/getTask"))
            .header("Authorization", Dotenv.load().get("SECRET_BYPASS"))
            .GET()
            .build();

        try {
            HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) { // succeeded
                String responseBody = response.body();
                ObjectMapper mapper = new ObjectMapper();
                System.out.println("TASK : " + responseBody);
                Task receivedTask = mapper.readValue(responseBody, Task.class);
                return receivedTask;

            } else {
                System.err.println("Error: HTTP status" + response.statusCode());
            }
        } catch (Exception e) {
            System.err.println(e);
        }

        return null;
    }

}
